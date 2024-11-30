package com.vtluan.place_order_football.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.TypeField;
import com.vtluan.place_order_football.repository.TypeFieldRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TypeFieldService {
    private final TypeFieldRepository typeFieldRepository;

    public Optional<TypeField> getTypeFieldById(long id) {
        return this.typeFieldRepository.findById(id);
    }
}
