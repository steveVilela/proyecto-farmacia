package com.proyecto.farmacia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.farmacia.interfaceService.DetalleVentaService;
import com.proyecto.farmacia.model.DetalleVenta;
import com.proyecto.farmacia.repository.DetalleVentaRepository;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService{

	@Autowired 
	private DetalleVentaRepository detalleVentaRepository;
	
	@Override
	public DetalleVenta save(DetalleVenta detalleVenta) {
		return detalleVentaRepository.save(detalleVenta);
	}

	@Override
	public List<DetalleVenta> findAll() {
		return detalleVentaRepository.findAll()	;
	}

}
