package com.example.nodo_springboot.repository;

import com.example.nodo_springboot.entities.KetQuaHocTap;
import com.example.nodo_springboot.entities.KetQuaHocTapId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KQHTRepository extends JpaRepository<KetQuaHocTap, KetQuaHocTapId> {

}
