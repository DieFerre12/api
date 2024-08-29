package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Detail;
import com.uade.tpo.demo.repository.DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailServiceImpl implements DetailService {

    @Autowired
    private DetailRepository detailRepository;

    @Override
    public Optional<Detail> getDetailById(Long id) {
        return detailRepository.findById(id);
    }

    @Override
    public Detail createDetail(Detail detail) {
        return detailRepository.save(detail);
    }

    @Override
    public Optional<Detail> updateDetail(Long detailId, Detail detail) {
        return detailRepository.findById(detailId).map(existingDetail -> {
            existingDetail.setId_order(detail.getId_order());
            existingDetail.setId_product(detail.getId_product());
            existingDetail.setQuantity(detail.getQuantity());
            existingDetail.setPrice(detail.getPrice());
            existingDetail.setAmount(detail.getAmount());
            existingDetail.setOrder(detail.getOrder());
            existingDetail.setProduct(detail.getProduct());
            existingDetail.setFacture(detail.getFacture());
            return detailRepository.save(existingDetail);
        });
    }

    @Override
    public void deleteDetail(Long detailId) {
        detailRepository.deleteById(detailId);
    }
}
