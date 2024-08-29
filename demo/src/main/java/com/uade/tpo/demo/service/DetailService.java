package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Detail;

import java.util.Optional;

public interface DetailService {

   public Optional<Detail> getDetailById(Long id);

   public Detail createDetail(Detail detail);

    public Optional<Detail> updateDetail(Long id, Detail detail);

    public void deleteDetail(Long id);
}
