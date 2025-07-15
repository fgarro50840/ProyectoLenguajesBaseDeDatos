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
    
    public void crearCursores() {

        // 1. activar_usuarios_inactivos
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS activar_usuarios_inactivos");
        jdbcTemplate.execute("""
   CREATE PROCEDURE activar_usuarios_inactivos()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM usuarios WHERE activo = FALSE;
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE usuarios SET activo = TRUE WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 2. marcar_proyectos_vacios_inactivos
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS marcar_proyectos_vacios_inactivos");
        jdbcTemplate.execute("""
   CREATE PROCEDURE marcar_proyectos_vacios_inactivos()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM proyectos WHERE descripcion IS NULL OR descripcion = '';
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE proyectos SET activo = FALSE WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 3. ajustar_presupuestos_bajos
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS ajustar_presupuestos_bajos");
        jdbcTemplate.execute("""
   CREATE PROCEDURE ajustar_presupuestos_bajos()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM presupuestos WHERE valor < 100;
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE presupuestos SET valor = 100 WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 4. validar_noticias_sin_titulo
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS validar_noticias_sin_titulo");
        jdbcTemplate.execute("""
   CREATE PROCEDURE validar_noticias_sin_titulo()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM noticias WHERE titulo IS NULL OR titulo = '';
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE noticias SET titulo = 'Sin título' WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 5. refrescar_rendimientos
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS refrescar_rendimientos");
        jdbcTemplate.execute("""
   CREATE PROCEDURE refrescar_rendimientos()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM indicadores;
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE indicadores SET rendimiento = rendimiento + 1 WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 6. etiquetar_alarmas_urgentes (antes activar_alarmas)
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS etiquetar_alarmas_urgentes");
        jdbcTemplate.execute("""
   CREATE PROCEDURE etiquetar_alarmas_urgentes()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM alarmas WHERE criticidad = 'ALTA';
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE alarmas SET descripcion = CONCAT('[URGENTE] ', descripcion) WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 7. limpiar_opciones_menu
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS limpiar_opciones_menu");
        jdbcTemplate.execute("""
   CREATE PROCEDURE limpiar_opciones_menu()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM opciones_menu WHERE nombre IS NULL;
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE opciones_menu SET nombre = 'Sin nombre' WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 8. marcar_reportes_inactivos
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS marcar_reportes_inactivos");
        jdbcTemplate.execute("""
   CREATE PROCEDURE marcar_reportes_inactivos()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM reportes WHERE informacion IS NULL;
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE reportes SET activo = FALSE WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 9. activar_informaciones
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS activar_informaciones");
        jdbcTemplate.execute("""
   CREATE PROCEDURE activar_informaciones()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM informaciones WHERE info_texto IS NOT NULL;
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE informaciones SET activo = TRUE WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 10. corregir_roles_null
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS corregir_roles_null");
        jdbcTemplate.execute("""
   CREATE PROCEDURE corregir_roles_null()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM roles WHERE tipo IS NULL;
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE roles SET tipo = 'Invitado' WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 11. normalizar_nombres_energias (nuevo)
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS normalizar_nombres_energias");
        jdbcTemplate.execute("""
   CREATE PROCEDURE normalizar_nombres_energias()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE v_nombre VARCHAR(255);
       DECLARE cur CURSOR FOR SELECT id, nombre FROM energias WHERE nombre = LOWER(nombre);
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id, v_nombre;
           IF done THEN LEAVE leer; END IF;
           UPDATE energias SET nombre = CONCAT(UPPER(SUBSTRING(v_nombre, 1, 1)), LOWER(SUBSTRING(v_nombre, 2))) WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 12. limpiar_informaciones_vacias (nuevo)
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS limpiar_informaciones_vacias");
        jdbcTemplate.execute("""
   CREATE PROCEDURE limpiar_informaciones_vacias()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM informaciones WHERE info_texto IS NULL OR info_texto = '';
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE informaciones SET info_texto = 'Sin información disponible' WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 13. cambiar_criticidad_baja
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS cambiar_criticidad_baja");
        jdbcTemplate.execute("""
   CREATE PROCEDURE cambiar_criticidad_baja()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM alarmas WHERE criticidad IS NULL;
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE alarmas SET criticidad = 'BAJA' WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 14. inicializar_indicadores
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS inicializar_indicadores");
        jdbcTemplate.execute("""
   CREATE PROCEDURE inicializar_indicadores()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM indicadores WHERE rendimiento IS NULL;
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE indicadores SET rendimiento = 0 WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        // 15. actualizar_titulos_noticias
        jdbcTemplate.execute("DROP PROCEDURE IF EXISTS actualizar_titulos_noticias");
        jdbcTemplate.execute("""
   CREATE PROCEDURE actualizar_titulos_noticias()
   BEGIN
       DECLARE done INT DEFAULT FALSE;
       DECLARE v_id BIGINT;
       DECLARE cur CURSOR FOR SELECT id FROM noticias WHERE titulo = '';
       DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


       OPEN cur;
       leer: LOOP
           FETCH cur INTO v_id;
           IF done THEN LEAVE leer; END IF;
           UPDATE noticias SET titulo = 'Título no definido' WHERE id = v_id;
       END LOOP;
       CLOSE cur;
   END
""");

        System.out.println("✅ Cursores creados correctamente (15/15)");
    }


}
