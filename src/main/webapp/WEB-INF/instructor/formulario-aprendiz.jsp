<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${empty aprendiz ? 'Nuevo' : 'Editar'} Aprendiz - Sistema de Roles</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome para iconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        .navbar-brand {
            font-weight: bold;
        }
        .card {
            margin-bottom: 20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .card-header {
            font-weight: bold;
            background-color: #f8f9fa;
        }
        .user-info {
            display: flex;
            align-items: center;
        }
        .user-info i {
            margin-right: 5px;
        }
        .required-field::after {
            content: " *";
            color: red;
        }
    </style>
</head>
<body>
    <!-- Barra de navegación -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="#"><i class="fas fa-users"></i> Sistema de Roles</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/instructor/aprendices">
                            <i class="fas fa-user-graduate"></i> Gestión de Aprendices
                        </a>
                    </li>
                </ul>
                <div class="user-info text-white">
                    <i class="fas fa-user-tie"></i> ${sessionScope.usuario.username} (${sessionScope.usuario.rol})
                    <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light ms-3">
                        <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
                    </a>
                </div>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <!-- Mensaje de alerta -->
        <c:if test="${not empty mensaje}">
            <div class="alert alert-${tipo} alert-dismissible fade show" role="alert">
                ${mensaje}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        
        <!-- Tarjeta del formulario -->
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">
                    <i class="fas fa-${empty aprendiz ? 'plus' : 'edit'}"></i> 
                    ${empty aprendiz ? 'Nuevo' : 'Editar'} Aprendiz
                </h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/instructor/aprendices" method="post">
                    <input type="hidden" name="accion" value="guardar">
                    <input type="hidden" name="id" value="${aprendiz.id}">
                    
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="cedula" class="form-label required-field">Cédula</label>
                            <input type="text" class="form-control" id="cedula" name="cedula" 
                                   value="${aprendiz.cedula}" required ${not empty aprendiz ? 'readonly' : ''}>
                            <div class="form-text">Número de identificación del aprendiz</div>
                        </div>
                        <div class="col-md-6">
                            <label for="nombre" class="form-label required-field">Nombre Completo</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" 
                                   value="${aprendiz.nombre}" required>
                        </div>
                    </div>
                    
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="direccion" class="form-label required-field">Dirección</label>
                            <input type="text" class="form-control" id="direccion" name="direccion" 
                                   value="${aprendiz.direccion}" required>
                        </div>
                        <div class="col-md-6">
                            <label for="email" class="form-label required-field">Email</label>
                            <input type="email" class="form-control" id="email" name="email" 
                                   value="${aprendiz.email}" required>
                        </div>
                    </div>
                    
                    <div class="d-flex justify-content-end mt-4">
                        <a href="${pageContext.request.contextPath}/instructor/aprendices" class="btn btn-secondary me-2">
                            <i class="fas fa-arrow-left"></i> Cancelar
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> Guardar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap 5 JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>