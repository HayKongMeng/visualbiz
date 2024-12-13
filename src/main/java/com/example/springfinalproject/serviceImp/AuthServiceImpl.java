package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.exception.BadRequestException;
import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.jwt.JwtService;
import com.example.springfinalproject.model.Entity.AppUser;
import com.example.springfinalproject.model.Entity.Otp;
import com.example.springfinalproject.model.Entity.Role;
import com.example.springfinalproject.model.Entity.UserInfoWithRole;
import com.example.springfinalproject.model.request.AuthRequest;
import com.example.springfinalproject.model.request.AppUserRequest;
import com.example.springfinalproject.model.request.OtpRequest;
import com.example.springfinalproject.model.request.PasswordRequest;
import com.example.springfinalproject.model.response.AppUserResponse;
import com.example.springfinalproject.model.response.AuthResponse;
import com.example.springfinalproject.repository.AppUserRepository;
import com.example.springfinalproject.repository.OtpRepository;
import com.example.springfinalproject.repository.RoleRepository;
import com.example.springfinalproject.services.AuthService;
import com.example.springfinalproject.services.EmailService;
import com.example.springfinalproject.utils.GetCurrentUser;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;
    private final EmailService emailService;
    private final OtpRepository otpRepository;
    private final RoleRepository roleRepository;

    private void authenticate(String email, String password) {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null) {
            throw new NotFoundException("Invalid email");
        }
        if (!passwordEncoder.matches(password, appUser.getPassword())) {
            throw new NotFoundException("Invalid Password");
        }
        Otp otp = otpRepository.findOptByUserId(appUser.getUserId());
        if (!otp.getVerify()){
            throw new BadRequestException("Your account is not verify yet");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {

        authenticate(authRequest.getEmail(), authRequest.getPassword());
        AppUser appUser = appUserRepository.findByEmail(authRequest.getEmail());
        if(appUser == null){
            throw new NotFoundException("User does not exist");
        }
        String token = jwtService.generateToken(appUser);
        return new AuthResponse(token);
    }

    @Override
    public AppUserResponse register(AppUserRequest appUserRequest) throws MessagingException {
        if (!appUserRequest.getConfirmPassword().equals(appUserRequest.getPassword())){
            throw new BadRequestException("Your confirm password does not match with your password");
        }
        AppUser user = appUserRepository.findByEmail(appUserRequest.getEmail());
        if (user != null) {
            throw new BadRequestException("This email is already registered");
        }
        appUserRequest.setPassword(passwordEncoder.encode(appUserRequest.getPassword()));
        AppUser appUser = appUserRepository.register(appUserRequest);
        String optCode = generateOTP();
        otpRepository.saveOtp(new OtpRequest(
                optCode,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5L),
                false,
                appUser.getUserId()
        ));
        emailService.sendMail(optCode, appUserRequest.getEmail(), "Verify your email with otp code");
        AppUserResponse userResponse  = appUserRepository.findUserByIdAuth(appUser.getUserId());
        roleRepository.insertUserRole(1,appUser.getUserId());
        return userResponse;
    }

    @Override
    public void verify(String otpCode) {
        Otp otp = otpRepository.findOtpByOtpCode(otpCode);
        System.out.println("=== " + otp);
        if (otp == null){
            throw new NotFoundException("Invalid otp code");
        }
        if (otp.getVerify()){
            throw new BadRequestException("Your account is verify already");
        }
        if(!otp.getExpiration().isAfter(LocalDateTime.now())){
            throw new BadRequestException("Your opt code is expire");
        }

        otpRepository.verify(otpCode);
    }

    @Override
    public void resend(String email) throws MessagingException {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null) {
            throw new NotFoundException("Invalid email");
        }
        Otp otp = otpRepository.findOptByUserId(appUser.getUserId());
        if(otp!=null){
            String optCode = generateOTP();
            otpRepository.updateOtp(new OtpRequest(
                    optCode,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(5L),
                    false,
                    appUser.getUserId()
            ));
            emailService.sendMail(optCode, appUser.getEmail(), "Verify your email with otp code");
        }else{
            throw new NotFoundException ( otp.getUserId() + " Not found");
        }

    }

    @Override
    public boolean forget(String email, PasswordRequest passwordRequest) throws MessagingException{
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null) {
            throw new NotFoundException("Invalid email");
        }
        Otp otp = otpRepository.findOptByUserId(appUser.getUserId());
        if(otp==null){
            throw new NotFoundException("Invalid otp");
        }
        System.out.println("otp expire " + otp.getExpiration());
        System.out.println("otp now " + LocalDateTime.now());
        System.out.println("isAfter = " + LocalDateTime.now().isAfter(otp.getExpiration()));
        if(LocalDateTime.now().isAfter(otp.getExpiration())) {

            String optCode = generateOTP();
            otpRepository.updateOtp(new OtpRequest(
                    optCode,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(5L),
                    false,
                    appUser.getUserId()
            ));
            emailService.sendMail(optCode, appUser.getEmail(), "Verify your email with otp code");
            Otp checkVerify = otpRepository.findOptByUserId(appUser.getUserId());

            if(!checkVerify.getVerify()){
                throw new BadRequestException("Your account not verify yet");
            }

        }
        System.out.println("otp getverify " + otp.getVerify());
        System.out.println("!otp getverify " + !otp.getVerify());

        if (!passwordRequest.getConfirmPassword().equals(passwordRequest.getPassword())){
            throw new BadRequestException("Your confirm password does not match with your password");
        }
        System.out.println("isBefore = " + LocalDateTime.now().isBefore(otp.getExpiration()));
        boolean update = false;
        if(LocalDateTime.now().isBefore(otp.getExpiration())) {
          update=  appUserRepository.forget(email, passwordEncoder.encode( passwordRequest.getPassword()));
        }
        System.out.println("after update = " + update);
        return update;
    }

    @Override
    public void reset(PasswordRequest passwordRequest) {
        if (!passwordRequest.getConfirmPassword().equals(passwordRequest.getPassword())){
            throw new BadRequestException("Your confirm password does not match with your password");
        }
        Integer currentUserId = GetCurrentUser.currentId();
        String email = appUserRepository.findEmailByUserId(currentUserId);
        appUserRepository.forget(email,passwordEncoder.encode(passwordRequest.getPassword()));
    }

    @Override
    public boolean verifyForgetPasswordOtp(String email, String otpCode, PasswordRequest passwordRequest) {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null) {
            throw new NotFoundException("Invalid email");
        }
        Otp otp = otpRepository.findOtpByOtpCode(otpCode);
        if (otp == null) {
            throw new NotFoundException("Invalid OTP code");
        }
        if (otp.getVerify()) {
            throw new BadRequestException("OTP code already used");
        }
        if (!otp.getExpiration().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("OTP code has expired");
        }
        if (!passwordRequest.getConfirmPassword().equals(passwordRequest.getPassword())) {
            throw new BadRequestException("Passwords do not match");
        }
        System.out.println("verify value = " + otp.getVerify());
        if(!otp.getVerify()){
            throw new BadRequestException("Please verify your account with OTPCode");
        }
        otpRepository.verify(otpCode);
        appUserRepository.forget(email, passwordEncoder.encode(passwordRequest.getPassword()));
        return true;
    }


    public static String generateOTP() {
        // declare randomNo to store the otp
        // generate 6 digits otp
        int randomNo = (int) (Math.random() * 900000) + 100000;
        // return otp
        return String.valueOf(randomNo);
    }
    @Override
    public UserInfoWithRole userInfoWithRole() {
       List<Role> getRoleForOneUser = roleRepository.getUserWithRole(GetCurrentUser.currentId());
        if(getRoleForOneUser.isEmpty()){
            throw new NotFoundException("Invalid user role");
        }
        UserInfoWithRole userInfoWithRole = new UserInfoWithRole();
        AppUserResponse userResponse  = appUserRepository.findUserByIdAuth(GetCurrentUser.currentId());
        if(userResponse==null){
            throw new NotFoundException("Invalid user");
        }
        userInfoWithRole.setUserId(GetCurrentUser.currentId());
        userInfoWithRole.setUserName(userResponse.getUserName());
        userInfoWithRole.setEmail(userResponse.getEmail());
        userInfoWithRole.setGender(userResponse.getGender());
        userInfoWithRole.setAddress(userResponse.getAddress());
        userInfoWithRole.setProfileImage(userResponse.getProfileImage());
        userInfoWithRole.setCurrentRole(Collections.singletonList(getRoleForOneUser));
        return userInfoWithRole;
    }
}
