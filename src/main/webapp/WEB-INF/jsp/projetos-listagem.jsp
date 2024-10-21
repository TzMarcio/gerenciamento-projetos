<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listagem de Projetos</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Listagem de Projetos</h2>

    <a href="/projetos/novo" class="btn btn-primary mb-3">NOVO PROJETO</a>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Nome</th>
            <th>Data Início</th>
            <th>Gerente</th>
            <th>Status</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="projeto" items="${projetos}">
            <tr id="projeto-${projeto.id}">
                <td>${projeto.nome}</td>
                <td>${projeto.dataInicio}</td>
                <td>${projeto.gerente != null ? projeto.gerente.nome : '-'}</td>
                <td>${projeto.status != null ? projeto.status.descricao : '-'}</td>
                <td class="text-center">
                    <a href="/projetos/visualizar/${projeto.id}" class="btn btn-info btn-sm">Visualizar</a>
                    <a href="/projetos/editar/${projeto.id}" class="btn btn-warning btn-sm">Editar</a>
                    <button class="btn btn-danger btn-sm" onclick="excluirProjeto(${projeto.id})">Excluir</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="/js/projetos-listagem.js"></script>
</body>
</html>
