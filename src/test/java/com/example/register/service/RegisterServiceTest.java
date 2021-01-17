package com.example.register.service;

import com.example.register.persistence.Register;
import com.example.register.persistence.RegisterRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class RegisterServiceTest {

    private RegisterRepository repository;
    private RegisterService service;

    @BeforeEach
    public void init() {
        repository = mock(RegisterRepository.class);
        service = new RegisterService(repository);
    }

    @Test
    public void recharge_RegisterDoesNotExist() {
        given(repository.findById("xyz")).willReturn(Optional.empty());

        var exc = assertThrows(IllegalArgumentException.class, () -> service.recharge("xyz", 500));
        assertThat(exc.getMessage(), is("Register 'xyz' does not exist"));
    }

    @Test
    public void recharge() {
        given(repository.findById("xyz")).willReturn(Optional.of(new Register("xyz", 1000)));

        service.recharge("xyz", 500);

        verify(repository).findById("xyz");
        verify(repository).save(refEq(new Register("xyz", 1500)));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void transfer_SrcRegisterDoesNotExist() {
        given(repository.findById("abc")).willReturn(Optional.empty());
        given(repository.findById("xyz")).willReturn(Optional.empty());

        var exc = assertThrows(IllegalArgumentException.class, () -> service.transfer("abc", "xyz", 50));
        assertThat(exc.getMessage(), is("Source register 'abc' does not exist"));
    }

    @Test
    public void transfer_DstRegisterDoesNotExist() {
        given(repository.findById("abc")).willReturn(Optional.of(new Register("abc", 1000)));
        given(repository.findById("xyz")).willReturn(Optional.empty());

        var exc = assertThrows(IllegalArgumentException.class, () -> service.transfer("abc", "xyz", 50));
        assertThat(exc.getMessage(), is("Destination register 'xyz' does not exist"));
    }

    @Test
    public void transfer() {
        given(repository.findById("abc")).willReturn(Optional.of(new Register("abc", 1000)));
        given(repository.findById("xyz")).willReturn(Optional.of(new Register("xyz", 200)));

        service.transfer("abc", "xyz", 50);

        verify(repository).findById("abc");
        verify(repository).findById("xyz");
        verify(repository).save(refEq(new Register("abc", 950)));
        verify(repository).save(refEq(new Register("xyz", 250)));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void getCurrentBalances() {
        given(repository.findAll()).willReturn(List.of(new Register("abc", 50), new Register("xyz", 100)));

        var balances = service.getCurrentBalances();

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
        assertThat(balances, is(Map.of("abc", 50, "xyz", 100)));
    }
}
