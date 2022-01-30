package com.project.barter;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class CommonField {

    protected static ResponseFieldsSnippet responseFieldData(FieldDescriptor[]... fieldArrays){
        List<FieldDescriptor> fieldDescriptorList = fieldArrayMerge(fieldArrays);
        ResponseFieldsSnippet dataFieldsSnippet = responseFields().andWithPrefix("data.", fieldDescriptorList);
        ResponseFieldsSnippet responseFieldsSnippet = dataFieldsSnippet.and(
                fieldWithPath("status").description("Http 상태코드"),
                fieldWithPath("message").description("응답 메세지")
        );
        return responseFieldsSnippet;
    }

    protected static ResponseFieldsSnippet responseDataListField(FieldDescriptor[]... fieldArrays){
        List<FieldDescriptor> fieldDescriptorList = fieldArrayMerge(fieldArrays);
        ResponseFieldsSnippet dataFieldsSnippet = responseFields().andWithPrefix("data.[].", fieldDescriptorList);
        ResponseFieldsSnippet responseFieldsSnippet = dataFieldsSnippet.and(
                fieldWithPath("status").description("Http 상태코드"),
                fieldWithPath("message").description("응답 메세지")
        );
        return responseFieldsSnippet;
    }

    public static List<FieldDescriptor> fieldArrayMerge(FieldDescriptor[]... fieldArrays){
        List<FieldDescriptor> fieldDescriptorList = new ArrayList<>();
        for (FieldDescriptor[] fieldArray : fieldArrays) {
            for (FieldDescriptor fieldDescriptor : fieldArray) {
                fieldDescriptorList.add(fieldDescriptor);
            }
        }
        return fieldDescriptorList;
    }

}
