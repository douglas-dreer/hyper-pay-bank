package br.com.dreerd.bank.hyper.controller;

import br.com.dreerd.bank.hyper.controller.common.BaseControllerTest;
import br.com.dreerd.bank.hyper.exception.BusinessException;
import br.com.dreerd.bank.hyper.model.ContactDTO;
import br.com.dreerd.bank.hyper.service.common.ContactService;
import br.com.dreerd.bank.hyper.utility.GeneratorInformation;
import org.apache.commons.lang.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest extends GeneratorInformation implements BaseControllerTest {
    private final static String ENDPOINT = "/contacts";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService service;

    private ContactDTO dto;
    private List<ContactDTO> dtoList = new ArrayList<>();

    final String MSG_ERROR_NOT_FOUND = "Contact doesn't found";
    final String MSG_ERROR_INTERNAL_ERROR = "Not has possible save operation";

    @BeforeEach
    public void setUp() {
        dto = createContact(ContactDTO.class);
        dtoList.add(dto);
    }

    @Test
    @Override
    public void mustReturnSuccessWhenList() throws Exception {
        MockHttpServletRequestBuilder getMethod = get(ENDPOINT);

        when(service.list()).thenReturn(dtoList);

        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    @Override
    public void mustReturnSuccessWhenFindById() throws Exception {
        final String PATH = String.format("%s/{id}", ENDPOINT);
        MockHttpServletRequestBuilder getMethod = get(PATH, dto.getId());

        when(service.findById(dto.getId())).thenReturn(dto);

        mockMvc.perform(getMethod)
                .andExpect(status().isOk());
    }

    @Override
    @Test
    public void mustReturnBusinessExceptionNotFoundWhenFindById() throws Exception {
        final String PATH = String.format("%s/{id}", ENDPOINT);
        MockHttpServletRequestBuilder getMethod = get(PATH, dto.getId());

        doThrow(new BusinessException(HttpStatus.NOT_FOUND, MSG_ERROR_NOT_FOUND)).when(service).findById(dto.getId());

        mockMvc.perform(getMethod)
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.details").value(MSG_ERROR_NOT_FOUND))
                .andExpect(status().isOk());
    }

    @Test
    @Override
    public void mustReturnSuccessWhenSave() throws Exception {
        when(service.save(any())).thenReturn(dto);

        MockHttpServletRequestBuilder postMethod = post(ENDPOINT)
                .content(dto.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(postMethod)
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Override
    public void mustReturnBusinessExceptionInternalErrorWhenSave() throws Exception {

        doThrow(new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, MSG_ERROR_INTERNAL_ERROR)).when(service).save(any());

        MockHttpServletRequestBuilder postMethod = post(ENDPOINT)
                .content(dto.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(postMethod)
                .andDo(print())
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(status().isOk());
    }

    @Test
    @Override
    public void mustReturnSuccessWhenEdit() throws Exception {
        when(service.edit(any())).thenReturn(dto);
        final String PATH = String.format("%s/{id}", ENDPOINT);

        MockHttpServletRequestBuilder postMethod = put(PATH, dto.getId())
                .content(dto.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(postMethod)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Override
    public void mustReturnBusinessExceptionBadRquestWhenEdit() throws Exception {
        final String PATH = String.format("%s/{id}", ENDPOINT);

        MockHttpServletRequestBuilder postMethod = put(PATH, UUID.randomUUID())
                .content(dto.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(postMethod)
                .andDo(print())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(status().isOk());
    }

    @Override
    public void mustReturnBusinessExceptionNotFoundWhenEdit() throws Exception {
        final String PATH = String.format("%s/{id}", ENDPOINT);
        doThrow(new BusinessException(HttpStatus.NOT_FOUND, MSG_ERROR_NOT_FOUND)).when(service).edit(dto);

        MockHttpServletRequestBuilder postMethod = put(PATH, UUID.randomUUID())
                .content(dto.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(postMethod)
                .andDo(print())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.details").value(MSG_ERROR_NOT_FOUND))
                .andExpect(status().isOk());
    }

    @Test
    @Override
    public void mustReturnSuccessWhenDelete() throws Exception {
        doNothing().when(service).delete(dto.getId());
        final String PATH = String.format("%s/{id}", ENDPOINT);

        MockHttpServletRequestBuilder postMethod = delete(PATH, dto.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(postMethod)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Override
    public void mustReturnBusinessExceptionNotFoundWhenDelete() throws Exception {
        doThrow(new BusinessException(HttpStatus.NOT_FOUND, MSG_ERROR_NOT_FOUND)).when(service).delete(dto.getId());
        final String PATH = String.format("%s/{id}", ENDPOINT);

        MockHttpServletRequestBuilder postMethod = delete(PATH, dto.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(postMethod)
                .andDo(print())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.details").value(MSG_ERROR_NOT_FOUND))
                .andExpect(status().isOk());
    }
}
