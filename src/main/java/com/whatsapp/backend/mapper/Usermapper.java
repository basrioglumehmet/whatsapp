package com.whatsapp.backend.mapper;

import com.whatsapp.backend.dao.model.Account;
import com.whatsapp.backend.dto.RegisterAccountRequest;
import com.whatsapp.backend.dto.RegisterAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Usermapper {
    Usermapper INSTANCE = Mappers.getMapper(Usermapper.class);
    Account mapRegisterRequestToAccount(RegisterAccountRequest registerAccountRequest);
    RegisterAccountResponse mapAccountToRegisterAccountResponse(Account account);
}
