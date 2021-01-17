package com.example.register.service;

import com.example.register.persistence.Register;
import com.example.register.persistence.RegisterRepository;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * service class which implements required register functionality all public
 * methods are transactional by using appropriate annotation
 */
@Service
@Transactional()
public class RegisterService {

    private final RegisterRepository repository;

    public RegisterService(RegisterRepository repository) {
        this.repository = repository;
    }

    public void recharge(String registerName, int amount) {
        // basic validation
        // note that amount is not validated. It may be negative and register after update may have negative balance.
        var registerOpt = repository.findById(registerName);
        Assert.isTrue(registerOpt.isPresent(), String.format("Register '%s' does not exist", registerName));
        // database update
        var updatedRegister = new Register(registerName, registerOpt.get().getBalance() + amount);
        repository.save(updatedRegister);
    }

    public void transfer(String srcRegisterName, String dstRegisterName, int amount) {
        // basic validation
        // note that amount is not validated. It may be negative. After transfer register may have negative balance.
        var srcRegisterOpt = repository.findById(srcRegisterName);
        Assert.isTrue(srcRegisterOpt.isPresent(), String.format("Source register '%s' does not exist", srcRegisterName));
        var dstRegisterOpt = repository.findById(dstRegisterName);
        Assert.isTrue(dstRegisterOpt.isPresent(), String.format("Destination register '%s' does not exist", dstRegisterName));
        // database update
        var updatedSrcRegister = new Register(srcRegisterName, srcRegisterOpt.get().getBalance() - amount);
        var updatedDstRegister = new Register(dstRegisterName, dstRegisterOpt.get().getBalance() + amount);
        repository.save(updatedSrcRegister);
        repository.save(updatedDstRegister);
    }

    public Map<String, Integer> getCurrentBalances() {
        var m = new LinkedHashMap<String, Integer>();
        for (var register : repository.findAll()) {
            m.put(register.getName(), register.getBalance());
        }
        return Collections.unmodifiableMap(m);
    }
}
