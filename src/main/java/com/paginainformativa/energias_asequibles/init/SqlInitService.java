package com.paginainformativa.energias_asequibles.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SqlInitService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //=========================================== Procedimientos almacenados ===========================================

    public void crearProcedimientosUsuario() {
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS guardar_usuario");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE guardar_usuario(
                        IN p_id BIGINT,
                        IN p_nombre VARCHAR(255),
                        IN p_contrasena VARCHAR(255),
                        IN p_rol_id BIGINT,
                        IN p_activo BIT
                    )
                    BEGIN
                        IF p_id IS NULL OR p_id = 0 THEN
                            INSERT INTO usuarios (nombre, contrasena, rol_id, activo)
                            VALUES (p_nombre, p_contrasena, p_rol_id, TRUE);
                            SELECT * FROM usuarios WHERE id = LAST_INSERT_ID();
                        ELSE
                            UPDATE usuarios
                            SET nombre = p_nombre,
                                contrasena = p_contrasena,
                                rol_id = p_rol_id,
                                activo = p_activo
                            WHERE id = p_id;
                            SELECT * FROM usuarios WHERE id = p_id;
                        END IF;
                    END
                """);

        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS usuario_por_id");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE usuario_por_id(IN p_id BIGINT)
                    BEGIN
                        SELECT * FROM usuarios WHERE id = p_id;
                    END
                """);

        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS usuario_por_nombre");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE usuario_por_nombre(IN p_nombre VARCHAR(255))
                    BEGIN
                        SELECT * FROM usuarios WHERE nombre = p_nombre;
                    END
                """);

        System.out.println("✅ Procedimientos de usuario creados");
    }


    public void crearProcedimientosAlarma() {
        // guardar_alarma
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS guardar_alarma");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE guardar_alarma(
                        IN p_id BIGINT,
                        IN p_nombre VARCHAR(255),
                        IN p_criticidad VARCHAR(255),
                        IN p_descripcion TEXT
                    )
                    BEGIN
                        IF p_id IS NULL OR p_id = 0 THEN
                            INSERT INTO alarmas (nombre, criticidad, descripcion)
                            VALUES (p_nombre, p_criticidad, p_descripcion);

                            SELECT * FROM alarmas WHERE id = LAST_INSERT_ID();
                        ELSE
                            UPDATE alarmas
                            SET nombre = p_nombre,
                                criticidad = p_criticidad,
                                descripcion = p_descripcion
                            WHERE id = p_id;

                            SELECT * FROM alarmas WHERE id = p_id;
                        END IF;
                    END
                """);

        // alarma_por_id
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS alarma_por_id");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE alarma_por_id(IN p_id BIGINT)
                    BEGIN
                        SELECT * FROM alarmas WHERE id = p_id;
                    END
                """);

        // eliminar_alarma
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS eliminar_alarma");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE eliminar_alarma(IN p_id BIGINT)
                    BEGIN
                        DELETE FROM alarmas WHERE id = p_id;
                    END
                """);

        System.out.println("✅ Procedimientos de alarma creados");
    }

    public void crearProcedimientosNoticias() {

        // guardar_noticia
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS guardar_noticia");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE guardar_noticia(
                        IN p_id BIGINT,
                        IN p_energia_id BIGINT,
                        IN p_titulo VARCHAR(255),
                        IN p_informacion LONGTEXT,
                        IN p_activo BOOLEAN
                    )
                    BEGIN
                        IF p_id IS NULL OR p_id = 0 THEN
                            INSERT INTO noticias (energia_id, titulo, informacion, activo)
                            VALUES (p_energia_id, p_titulo, p_informacion, TRUE);
                            SELECT * FROM noticias WHERE id = LAST_INSERT_ID();
                        ELSE
                            UPDATE noticias
                            SET energia_id = p_energia_id,
                                titulo = p_titulo,
                                informacion = p_informacion,
                                activo = p_activo
                            WHERE id = p_id;
                            SELECT * FROM noticias WHERE id = p_id;
                        END IF;
                    END
                """);

        // noticia_por_id
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS noticia_por_id");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE noticia_por_id(IN p_id BIGINT)
                    BEGIN
                        SELECT * FROM noticias WHERE id = p_id;
                    END
                """);

        // noticia_por_energia
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS noticia_por_energia");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE noticia_por_energia(IN p_energia_id BIGINT)
                    BEGIN
                        SELECT * FROM noticias WHERE energia_id = p_energia_id;
                    END
                """);

        System.out.println("✅ Procedimientos de noticias creados");
    }

    public void crearProcedimientosPresupuesto() {

        // guardar_presupuesto
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS guardar_presupuesto");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE guardar_presupuesto(
                        IN p_id BIGINT,
                        IN p_proyecto_id BIGINT,
                        IN p_valor DECIMAL(10,2),
                        IN p_activo BOOLEAN
                    )
                    BEGIN
                        IF p_id IS NULL OR p_id = 0 THEN
                            INSERT INTO presupuestos (proyecto_id, valor, activo)
                            VALUES (p_proyecto_id, p_valor, TRUE);
                            SELECT * FROM presupuestos WHERE id = LAST_INSERT_ID();
                        ELSE
                            UPDATE presupuestos
                            SET proyecto_id = p_proyecto_id,
                                valor = p_valor,
                                activo = p_activo
                            WHERE id = p_id;
                            SELECT * FROM presupuestos WHERE id = p_id;
                        END IF;
                    END
                """);

        // presupuesto_por_id
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS presupuesto_por_id");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE presupuesto_por_id(IN p_id BIGINT)
                    BEGIN
                        SELECT * FROM presupuestos WHERE id = p_id;
                    END
                """);

        // presupuesto_por_proyecto
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS presupuesto_por_proyecto");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE presupuesto_por_proyecto(IN p_proyecto_id BIGINT)
                    BEGIN
                        SELECT * FROM presupuestos WHERE proyecto_id = p_proyecto_id;
                    END
                """);

        System.out.println("✅ Procedimientos de presupuestos creados");
    }

    public void crearProcedimientosProyecto() {

        // guardar_proyecto
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS guardar_proyecto");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE guardar_proyecto(
                        IN p_id BIGINT,
                        IN p_energia_id BIGINT,
                        IN p_nombre VARCHAR(255),
                        IN p_descripcion LONGTEXT,
                        IN p_activo BOOLEAN
                    )
                    BEGIN
                        IF p_id IS NULL OR p_id = 0 THEN
                            INSERT INTO proyectos (energia_id, nombre, descripcion, activo)
                            VALUES (p_energia_id, p_nombre, p_descripcion, TRUE);
                            SELECT * FROM proyectos WHERE id = LAST_INSERT_ID();
                        ELSE
                            UPDATE proyectos
                            SET energia_id = p_energia_id,
                                nombre = p_nombre,
                                descripcion = p_descripcion,
                                activo = p_activo
                            WHERE id = p_id;
                            SELECT * FROM proyectos WHERE id = p_id;
                        END IF;
                    END
                """);

        // proyecto_por_id
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS proyecto_por_id");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE proyecto_por_id(IN p_id BIGINT)
                    BEGIN
                        SELECT * FROM proyectos WHERE id = p_id;
                    END
                """);

        // proyecto_por_energia
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS proyecto_por_energia");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE proyecto_por_energia(IN p_energia_id BIGINT)
                    BEGIN
                        SELECT * FROM proyectos WHERE energia_id = p_energia_id;
                    END
                """);

        System.out.println("✅ Procedimientos de proyectos creados");
    }

    public void crearProcedimientosOpcionMenu() {

        // guardar_opcion_menu
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS guardar_opcion_menu");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE guardar_opcion_menu(
                        IN p_id BIGINT,
                        IN p_nombre VARCHAR(255),
                        IN p_activo BOOLEAN
                    )
                    BEGIN
                        IF p_id IS NULL OR p_id = 0 THEN
                            INSERT INTO opciones_menu (nombre, activo)
                            VALUES (p_nombre, TRUE);
                            SELECT * FROM opciones_menu WHERE id = LAST_INSERT_ID();
                        ELSE
                            UPDATE opciones_menu
                            SET nombre = p_nombre,
                                activo = p_activo
                            WHERE id = p_id;
                            SELECT * FROM opciones_menu WHERE id = p_id;
                        END IF;
                    END
                """);

        // opcion_menu_por_id
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS opcion_menu_por_id");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE opcion_menu_por_id(IN p_id BIGINT)
                    BEGIN
                        SELECT * FROM opciones_menu WHERE id = p_id;
                    END
                """);

        // listar_opciones_menu_activas
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS listar_opciones_menu_activas");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE listar_opciones_menu_activas()
                    BEGIN
                        SELECT * FROM opciones_menu WHERE activo = TRUE;
                    END
                """);

        System.out.println("✅ Procedimientos de opciones_menu creados");
    }

    public void crearProcedimientosIndicador() {

        // guardar_indicador
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS guardar_indicador");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE guardar_indicador(
                        IN p_id BIGINT,
                        IN p_proyecto_id BIGINT,
                        IN p_rendimiento INT,
                        IN p_activo BOOLEAN
                    )
                    BEGIN
                        IF p_id IS NULL OR p_id = 0 THEN
                            INSERT INTO indicadores (proyecto_id, rendimiento, activo)
                            VALUES (p_proyecto_id, p_rendimiento, TRUE);
                            SELECT * FROM indicadores WHERE id = LAST_INSERT_ID();
                        ELSE
                            UPDATE indicadores
                            SET proyecto_id = p_proyecto_id,
                                rendimiento = p_rendimiento,
                                activo = p_activo
                            WHERE id = p_id;
                            SELECT * FROM indicadores WHERE id = p_id;
                        END IF;
                    END
                """);

        // indicador_por_id
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS indicador_por_id");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE indicador_por_id(IN p_id BIGINT)
                    BEGIN
                        SELECT * FROM indicadores WHERE id = p_id;
                    END
                """);

        // indicador_por_proyecto
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS indicador_por_proyecto");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE indicador_por_proyecto(IN p_proyecto_id BIGINT)
                    BEGIN
                        SELECT * FROM indicadores WHERE proyecto_id = p_proyecto_id;
                    END
                """);

        System.out.println("✅ Procedimientos de indicadores creados");
    }

    public void crearProcedimientosInformacion() {

        // guardar_informacion
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS guardar_informacion");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE guardar_informacion(
                        IN p_id BIGINT,
                        IN p_energia_id BIGINT,
                        IN p_info_texto LONGTEXT,
                        IN p_activo BOOLEAN
                    )
                    BEGIN
                        IF p_id IS NULL OR p_id = 0 THEN
                            INSERT INTO informaciones (energia_id, info_texto, activo)
                            VALUES (p_energia_id, p_info_texto, TRUE);
                            SELECT * FROM informaciones WHERE id = LAST_INSERT_ID();
                        ELSE
                            UPDATE informaciones
                            SET energia_id = p_energia_id,
                                info_texto = p_info_texto,
                                activo = p_activo
                            WHERE id = p_id;
                            SELECT * FROM informaciones WHERE id = p_id;
                        END IF;
                    END
                """);

        // informacion_por_id
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS informacion_por_id");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE informacion_por_id(IN p_id BIGINT)
                    BEGIN
                        SELECT * FROM informaciones WHERE id = p_id;
                    END
                """);

        // informacion_por_energia
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS informacion_por_energia");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE informacion_por_energia(IN p_energia_id BIGINT)
                    BEGIN
                        SELECT * FROM informaciones WHERE energia_id = p_energia_id;
                    END
                """);

        System.out.println("✅ Procedimientos de informaciones creados");
    }

    public void crearProcedimientosReporte() {

        // guardar_reporte
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS guardar_reporte");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE guardar_reporte(
                        IN p_id BIGINT,
                        IN p_proyecto_id BIGINT,
                        IN p_informacion TEXT,
                        IN p_activo BOOLEAN
                    )
                    BEGIN
                        IF p_id IS NULL OR p_id = 0 THEN
                            INSERT INTO reportes (proyecto_id, informacion, activo)
                            VALUES (p_proyecto_id, p_informacion, TRUE);
                            SELECT * FROM reportes WHERE id = LAST_INSERT_ID();
                        ELSE
                            UPDATE reportes
                            SET proyecto_id = p_proyecto_id,
                                informacion = p_informacion,
                                activo = p_activo
                            WHERE id = p_id;
                            SELECT * FROM reportes WHERE id = p_id;
                        END IF;
                    END
                """);

        // reporte_por_id
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS reporte_por_id");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE reporte_por_id(IN p_id BIGINT)
                    BEGIN
                        SELECT * FROM reportes WHERE id = p_id;
                    END
                """);

        // reporte_por_proyecto
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS reporte_por_proyecto");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE reporte_por_proyecto(IN p_proyecto_id BIGINT)
                    BEGIN
                        SELECT * FROM reportes WHERE proyecto_id = p_proyecto_id;
                    END
                """);

        System.out.println("✅ Procedimientos de reportes creados");
    }

    public void crearProcedimientosEnergia() {

        // guardar_energia
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS guardar_energia");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE guardar_energia(
                        IN p_id BIGINT,
                        IN p_nombre VARCHAR(255),
                        IN p_descripcion TEXT,
                        IN p_activo BOOLEAN
                    )
                    BEGIN
                        IF p_id IS NULL OR p_id = 0 THEN
                            INSERT INTO energias (nombre, descripcion, activo)
                            VALUES (p_nombre, p_descripcion, TRUE);
                            SELECT * FROM energias WHERE id = LAST_INSERT_ID();
                        ELSE
                            UPDATE energias
                            SET nombre = p_nombre,
                                descripcion = p_descripcion,
                                activo = p_activo
                            WHERE id = p_id;
                            SELECT * FROM energias WHERE id = p_id;
                        END IF;
                    END
                """);

        // energia_por_id
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS energia_por_id");
        jdbcTemplate.execute("""
                    CREATE PROCEDURE energia_por_id(IN p_id BIGINT)
                    BEGIN
                        SELECT * FROM energias WHERE id = p_id;
                    END
                """);

        System.out.println("✅ Procedimientos de energias creados");
    }

    // =========================================== Vistas ===========================================


    public void crearVistas() {
        // vista_alarmas
        jdbcTemplate.execute("DROP VIEW IF EXISTS vista_alarmas");
        jdbcTemplate.execute("""
                    CREATE VIEW vista_alarmas AS
                    SELECT
                        id,
                        nombre,
                        criticidad,
                        descripcion
                    FROM alarmas
                """);

        // vista_usuarios
        jdbcTemplate.execute("DROP VIEW IF EXISTS vista_usuarios");
        jdbcTemplate.execute("""
                    CREATE VIEW vista_usuarios AS
                    SELECT
                        id,
                        nombre,
                        contrasena,
                        rol_id,
                        activo
                    FROM usuarios
                """);

        // vista_noticias
        jdbcTemplate.execute("DROP VIEW IF EXISTS vista_noticias");
        jdbcTemplate.execute("""
                    CREATE VIEW vista_noticias AS
                    SELECT
                        id,
                        energia_id,
                        titulo,
                        informacion,
                        activo
                    FROM noticias
                """);

        // vista_presupuestos
        jdbcTemplate.execute("DROP VIEW IF EXISTS vista_presupuestos");
        jdbcTemplate.execute("""
                    CREATE VIEW vista_presupuestos AS
                    SELECT
                        id,
                        proyecto_id,
                        valor,
                        activo
                    FROM presupuestos
                """);

        // vista_proyectos
        jdbcTemplate.execute("DROP VIEW IF EXISTS vista_proyectos");
        jdbcTemplate.execute("""
                    CREATE VIEW vista_proyectos AS
                    SELECT
                        id,
                        energia_id,
                        nombre,
                        descripcion,
                        activo
                    FROM proyectos
                """);

        // vista_opciones_menu
        jdbcTemplate.execute("DROP VIEW IF EXISTS vista_opciones_menu");
        jdbcTemplate.execute("""
                    CREATE VIEW vista_opciones_menu AS
                    SELECT
                        id,
                        nombre,
                        activo
                    FROM opciones_menu
                """);

        // vista_indicadores
        jdbcTemplate.execute("DROP VIEW IF EXISTS vista_indicadores");
        jdbcTemplate.execute("""
                    CREATE VIEW vista_indicadores AS
                    SELECT
                        id,
                        proyecto_id,
                        rendimiento,
                        activo
                    FROM indicadores
                """);

        // vista_informaciones
        jdbcTemplate.execute("DROP VIEW IF EXISTS vista_informaciones");
        jdbcTemplate.execute("""
                    CREATE VIEW vista_informaciones AS
                    SELECT
                        id,
                        energia_id,
                        info_texto,
                        activo
                    FROM informaciones
                """);

        // vista_reportes
        jdbcTemplate.execute("DROP VIEW IF EXISTS vista_reportes");
        jdbcTemplate.execute("""
                    CREATE VIEW vista_reportes AS
                    SELECT
                        id,
                        proyecto_id,
                        informacion,
                        activo
                    FROM reportes
                """);

        // vista_energias
        jdbcTemplate.execute("DROP VIEW IF EXISTS vista_energias");
        jdbcTemplate.execute("""
                    CREATE VIEW vista_energias AS
                    SELECT
                        id,
                        nombre,
                        descripcion,
                        activo
                    FROM energias
                """);

        // vista_roles
        jdbcTemplate.execute("DROP VIEW IF EXISTS vista_roles");
        jdbcTemplate.execute("""
                    CREATE VIEW vista_roles AS
                    SELECT
                        id,
                        tipo
                    FROM roles
                """);


        System.out.println("✅ Vistas creadas exitosamente");
    }


}
