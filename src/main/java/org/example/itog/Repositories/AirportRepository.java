package org.example.itog.Repositories;

import org.example.itog.Models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {}
