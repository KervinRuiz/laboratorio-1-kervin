INSERT INTO usuarios (username, password, rol_id)
SELECT 'admin', '$2a$10$Fgq0NP3.tsIxFz7V1BXmiepZoWdpkUtceQx0IBtTNkB.yKZFB61YK', id
FROM roles WHERE nombre = 'SUPER-ADMIN-ROLE';

INSERT INTO usuarios (username, password, rol_id)
SELECT 'usuario', '$2a$10$c6NIsj9xx707mZUiQSJsK.Y9pBsqf3n/lqDYCfxfQ3Jots44BH5hG', id
FROM roles WHERE nombre = 'USER';
