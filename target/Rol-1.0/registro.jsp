<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registro de Usuario - Sistema de Roles</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome para iconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .registro-container {
            max-width: 500px;
            margin: 50px auto;
            padding: 30px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }
        .registro-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .registro-header h2 {
            color: #0d6efd;
        }
        .form-floating {
            margin-bottom: 20px;
        }
        .btn-registro {
            width: 100%;
            padding: 12px;
            font-weight: 600;
        }
        .back-to-login {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="registro-container">
            <div class="registro-header">
                <h2><i class="fas fa-user-plus"></i> Registro de Usuario</h2>
                <p class="text-muted">Complete el formulario para crear una cuenta</p>
            </div>
            
            <c:if test="${not empty mensaje}">
                <div class="alert alert-${tipo == null ? 'info' : tipo} alert-dismissible fade show" role="alert">
                    ${mensaje}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/registro" method="post">
                <div class="form-floating">
                    <input type="text" class="form-control" id="username" name="username" placeholder="Nombre de usuario" required>
                    <label for="username">Nombre de usuario</label>
                </div>
                <div class="form-floating">
                    <input type="email" class="form-control" id="email" name="email" placeholder="Correo electrónico" required>
                    <label for="email">Correo electrónico</label>
                </div>
                <div class="form-floating">
                    <select class="form-select" id="rol" name="rol" required>
                        <option value="" selected disabled>Seleccione un rol</option>
                        <option value="Instructor">Instructor</option>
                        <option value="Aprendiz">Aprendiz</option>
                    </select>
                    <label for="rol">Rol</label>
                </div>
                <button type="submit" class="btn btn-primary btn-registro">
                    <i class="fas fa-user-plus"></i> Registrarse
                </button>
            </form>
            
            <div class="back-to-login">
                <a href="${pageContext.request.contextPath}/login">
                    <i class="fas fa-arrow-left"></i> Volver al inicio de sesión
                </a>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap 5 JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>