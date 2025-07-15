package com.paginainformativa.energias_asequibles;

import com.paginainformativa.energias_asequibles.init.SqlInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EnergiasAsequiblesApplication implements CommandLineRunner {

    @Autowired
    private SqlInitService sqlInitService;

    public static void main(String[] args) {
        SpringApplication.run(EnergiasAsequiblesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        sqlInitService.crearProcedimientosUsuario();
        sqlInitService.crearProcedimientosAlarma();
        sqlInitService.crearProcedimientosNoticias();
        sqlInitService.crearProcedimientosPresupuesto();
        sqlInitService.crearProcedimientosProyecto();
        sqlInitService.crearProcedimientosOpcionMenu();
        sqlInitService.crearProcedimientosIndicador();
        sqlInitService.crearProcedimientosInformacion();
        sqlInitService.crearProcedimientosReporte();
        sqlInitService.crearProcedimientosEnergia();
        sqlInitService.crearVistas();
        sqlInitService.crearFunciones();
        sqlInitService.crearCursores();
        sqlInitService.crearTriggers();
    }
}