<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cambiar Contraseña</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f5f5f5;
            padding-top: 50px;
        }
        .card {
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .card-header {
            background-color: #007bff;
            color: white;
            border-radius: 10px 10px 0 0 !important;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h4 class="text-center">
                            <% if (request.getAttribute("passwordTemporal") != null && (Boolean)request.getAttribute("passwordTemporal")) { %>
                                Cambio Obligatorio de Contraseña
                            <% } else { %>
                                Cambiar Contraseña
                            <% } %>
                        </h4>
                    </div>
                    <div class="card-body">
                        <% if (request.getAttribute("mensaje") != null) { %>
                            <div class="alert alert-<%= request.getAttribute("tipo") %>">
                                <%= request.getAttribute("mensaje") %>
                            </div>
                        <% } %>
                        
                        <% if (request.getAttribute("passwordTemporal") != null && (Boolean)request.getAttribute("passwordTemporal")) { %>
                            <div class="alert alert-warning">
                                <strong>¡Atención!</strong> Por seguridad, debe cambiar su contraseña temporal por una nueva.
                            </div>
                        <% } %>
                        
                        <form action="${pageContext.request.contextPath}/cambiar-password" method="post">
                            <div class="mb-3">
                                <label for="username" class="form-label">Usuario</label>
                                <input type="text" class="form-control" id="username" name="username" value="${requestScope.username}" readonly>
                            </div>
                            
                            <% if (request.getAttribute("passwordTemporal") == null || !(Boolean)request.getAttribute("passwordTemporal")) { %>
                                <div class="mb-3">
                                    <label for="passwordActual" class="form-label">Contraseña Actual</label>
                                    <input type="password" class="form-control" id="passwordActual" name="passwordActual" required>
                                </div>
                            <% } else { %>
                                <!-- Cuando es contraseña temporal, enviamos un valor en el campo oculto para evitar validaciones -->
                                <input type="hidden" name="passwordActual" value="temporalPassword">
                            <% } %>
                            
                            <div class="mb-3">
                                <label for="passwordNuevo" class="form-label">Nueva Contraseña</label>
                                <input type="password" class="form-control" id="passwordNuevo" name="passwordNuevo" required>
                                <div class="form-text">La contraseña debe tener al menos 6 caracteres.</div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="passwordConfirmar" class="form-label">Confirmar Nueva Contraseña</label>
                                <input type="password" class="form-control" id="passwordConfirmar" name="passwordConfirmar" required>
                            </div>
                            
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">Cambiar Contraseña</button>
                                
                                <% if (request.getAttribute("passwordTemporal") == null || !(Boolean)request.getAttribute("passwordTemporal")) { %>
                                    <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary">Cancelar</a>
                                <% } %>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
