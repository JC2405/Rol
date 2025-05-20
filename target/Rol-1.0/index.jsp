<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sistema de Roles - SENA ADSO</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome para iconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .welcome-container {
            max-width: 600px;
            padding: 30px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .welcome-icon {
            font-size: 80px;
            color: #0d6efd;
            margin-bottom: 20px;
        }
        .welcome-title {
            font-size: 28px;
            color: #0d6efd;
            margin-bottom: 15px;
        }
        .welcome-message {
            font-size: 18px;
            margin-bottom: 30px;
            color: #6c757d;
        }
        .btn-login {
            padding: 10px 25px;
            font-weight: 600;
        }
    </style>
</head>
<body>
    <div class="welcome-container">
        <div class="welcome-icon">
            <i class="fas fa-users"></i>
        </div>
        <h1 class="welcome-title">Bienvenido al Sistema de Roles</h1>
        <p class="welcome-message">Sistema de gestión de aprendices con diferentes roles de acceso</p>
        <div class="d-grid gap-2">
            <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-login mb-2">
                <i class="fas fa-sign-in-alt me-2"></i>Iniciar Sesión
            </a>
            <a href="${pageContext.request.contextPath}/registro" class="btn btn-outline-primary btn-login">
                <i class="fas fa-user-plus me-2"></i>Registrarse
            </a>
        </div>
    </div>
    
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>