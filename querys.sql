SELECT
  public.empleado.nombres,
  public.empleado.apellidos,
  public.empleado.cedula,
  public.departamento.nombre_departamento,
  public.departamento.tipo
FROM
  public.funcion
  INNER JOIN public.empleado ON (public.funcion.cedula = public.empleado.cedula)
  INNER JOIN public.empleado_departamento ON (public.empleado.cedula = public.empleado_departamento.cedula)
  INNER JOIN public.departamento ON (public.empleado_departamento.id_departamento = public.departamento.id_departamento)
WHERE
  empleado_departamento.fecha_final IS NULL AND
  funcion.tipo = $P{funcion} AND
  Extract(day from public.docente_detalles.fecha_pago)=$P{dia}
  AND Extract(month from public.docente_detalles.fecha_pago)=$P{mes}
  AND  Extract(year from public.docente_detalles.fecha_pago)=$P{anio}
  
  
  --EN POSTGRES
--Para archivo resultado

=CONCATENAR("Select * from public.ingresar_valores_resultado('",B5,"',27,3,2014,",D5,",'Pago Fin de Marzo Administrativos');")
=CONCATENAR("Select * from public.ingresar_valores_resultado('",B3,"',11,4,2014,",D3,",'Pago Quincena de Abril Administrativos');")

=CONCATENAR("UPDATE  public.empleado SET num_cuenta ='",D3,"' WHERE   public.empleado.cedula ='",A3,"';")


=BUSCARV('1Q ENE2014 SERV'!$Z$5,Rubros!$A$1:$B$36,2,FALSO)

=CONCATENAR("Select * from public.agregar_quincena_lc('",A2,"',",B2,",","'15-03-2014',",C2,",'Q');")
cedula,rubro,fech,valor_rubro,tipo
=CONCATENAR("Select * from public.agregar_quincena_lc('",C4,"',",21,",","'28-02-2014',",E4,",'F');")

=CONCATENAR("Select * from public.""pa_guardarDetalleDocentes""('",A2,"','15-03-2014',",B2,",",C2,",",D2,",",E2,");")


delete from public.quincena where valor_rubro=0 AND id_rubro != 7

insert into public.quincena
(
  SELECT 
  public.empleado.cedula,
  public.departamento.id_departamento,
  public.empleado_departamento.estado,
    7 as rubro,
  '15/04/2014' as fecha_pago,
  0.0 as valor,
  'Q' as tipo_rubro
FROM
  public.empleado_departamento
  INNER JOIN public.departamento ON (public.empleado_departamento.id_departamento = public.departamento.id_departamento)
  INNER JOIN public.empleado ON (public.empleado_departamento.cedula = public.empleado.cedula)
  where
   public.empleado.cedula NOT IN (SELECT   public.quincena.cedula FROM  public.quincena  where   public.quincena.fecha_pago='15/04/2014' AND  public.quincena.id_rubro=7)
  )


Cedula	Id Rubro	Valor

--FIN POSTGRES