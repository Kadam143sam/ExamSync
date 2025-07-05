package com.examsync.service;

import com.examsync.dto.RegistrationForm;
import com.examsync.model.Institute;
import com.examsync.model.User;
import com.examsync.repository.InstituteRepository;
import com.examsync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // ✅ changed import
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final InstituteRepository instituteRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder; // ✅ correct type

    public void save(RegistrationForm form) {
        String hashed = encoder.encode(form.getPassword());

        if (form.getUserType() == RegistrationForm.UserType.INSTITUTE) {
            Institute inst = new Institute();
            inst.setName(form.getName());
            inst.setEmail(form.getEmail());
            inst.setPassword(hashed);
            inst.setCode(form.getInstituteCode());
            inst.setState(form.getState());
            instituteRepo.save(inst);
        } else {
            User user = new User();
            user.setName(form.getName());
            user.setEmail(form.getEmail());
            user.setPassword(hashed);
            user.setPhone(form.getPhone());
            userRepo.save(user);
        }
    }
}
