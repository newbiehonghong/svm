多数据源配置demo openMulti: true 表示启用，多数据源下需要增加com.neusoft.heimdall.jdbc的扫描，如果项目扫描已经涵盖可忽略

spring:
  datasource:
    password: ef82704271b93456
    url: jdbc:postgresql://10.4.56.44:5432/cloud?useUnicode=true&characterEncoding=UTF-8
    driver-class-name: org.postgresql.Driver
    username: base
    openMulti: true
    idle-timeout: 30000
    multi:
    - key: tenant
      password: ef82704271b93456
      url: jdbc:postgresql://10.4.56.44:5432/cloud?useUnicode=true&characterEncoding=UTF-8
      idle-timeout: 20000
      driver-class-name: org.postgresql.Driver
      username: tenant
    - key: tenant1
      password: ef82704271b93456
      url: jdbc:postgresql://10.4.56.44:5432/cloud?useUnicode=true&characterEncoding=UTF-8
      driver-class-name: org.postgresql.Driver
      username: tenant1