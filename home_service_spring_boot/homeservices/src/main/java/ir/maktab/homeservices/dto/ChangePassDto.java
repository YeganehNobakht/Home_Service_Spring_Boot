package ir.maktab.homeservices.dto;

import ir.maktab.homeservices.service.validation.ValidPassword;

public class ChangePassDto {
    @ValidPassword
    private String oldPass;
    @ValidPassword
    private String newPass;

    public String getOldPass() {
        return oldPass;
    }

    public ChangePassDto setOldPass(String oldPass) {
        this.oldPass = oldPass;
        return this;
    }

    public String getNewPass() {
        return newPass;
    }

    public ChangePassDto setNewPass(String newPass) {
        this.newPass = newPass;
        return this;
    }
}
