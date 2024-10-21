<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Projeto</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
<div class="container mt-5">
    <h2>${projeto != null && projeto.id != null ? (modo == 'visualizar' ? 'Visualizar Projeto' : 'Editar Projeto') : 'Criar Novo Projeto'}</h2>

    <form id="form" method="${projeto != null && projeto.id != null ? 'PUT' : 'POST'}">

        <input type="hidden" id="id" value="${projeto != null && projeto.id != null ? projeto.id : ''}">

        <div class="form-group">
            <label for="nome">Nome do Projeto</label>
            <input type="text" class="form-control" id="nome" name="nome" value="${projeto.nome}" ${modo == 'visualizar' ? 'readonly' : ''}>
        </div>

        <div class="form-group">
            <label for="dataInicio">Data de Início</label>
            <input type="date" class="form-control" id="dataInicio" name="dataInicio" value="${projeto.dataInicio}" ${modo == 'visualizar' ? 'readonly' : ''}>
        </div>

        <div class="form-group">
            <label for="dataPrevisaoFim">Previsão de Término</label>
            <input type="date" class="form-control" id="dataPrevisaoFim" name="dataPrevisaoFim" value="${projeto.dataPrevisaoFim}" ${modo == 'visualizar' ? 'readonly' : ''}>
        </div>

        <div class="form-group">
            <label for="dataFim">Data Fim</label>
            <input type="date" class="form-control" id="dataFim" name="dataFim" value="${projeto.dataFim}" ${modo == 'visualizar' ? 'readonly' : ''}>
        </div>

        <div class="form-group">
            <label for="descricao">Descrição</label>
            <textarea class="form-control" id="descricao" name="descricao" ${modo == 'visualizar' ? 'readonly' : ''}>${projeto.descricao}</textarea>
        </div>

        <div class="form-group">
            <label for="status">Status</label>
            <c:if test="${modo != 'visualizar'}">
                <select id="status" name="status" class="form-control">
                    <c:forEach var="status" items="${status}">
                        <option value="${status}" ${status == projeto.status ? 'selected="selected"' : ''}>
                            <c:out value="${status.descricao}" />
                        </option>
                    </c:forEach>
                </select>
            </c:if>

            <c:if test="${modo == 'visualizar'}">
                <input type="text" class="form-control" id="status" name="status" value="${projeto.status.descricao}" readonly>
            </c:if>
        </div>

        <div class="form-group">
            <label for="risco">Risco</label>
            <c:if test="${modo != 'visualizar'}">
                <select id="risco" name="risco" class="form-control"}>
                    <c:forEach var="risco" items="${riscos}">
                        <option value="${risco}" ${risco == projeto.risco ? 'selected="selected"' : ''}>
                            <c:out value="${risco.descricao}" />
                        </option>
                    </c:forEach>
                </select>
            </c:if>

            <c:if test="${modo == 'visualizar'}">
                <input type="text" class="form-control" id="risco" name="risco" value="${projeto.risco.descricao}" readonly>
            </c:if>
        </div>

        <div class="form-group">
            <label for="orcamento">Orçamento</label>
            <input type="number" class="form-control" id="orcamento" name="orcamento" value="${projeto.orcamento}" ${modo == 'visualizar' ? 'readonly' : ''}>
        </div>

        <div class="form-group">
            <label for="gerenteNome">Gerente Responsável</label>
            <input type="text" id="gerenteNome" name="gerenteNome" class="form-control autocomplete-input" placeholder="Selecione um gerente" value="${projeto.gerente != null ? projeto.gerente.nome : ''}" ${modo == 'visualizar' ? 'readonly' : ''}>
            <input type="hidden" id="gerenteId" name="gerenteId" value="${projeto.gerente != null ? projeto.gerente.id : ''}">
        </div>

        <c:if test="${modo != 'visualizar'}">
            <div class="form-group">
                <label for="funcionario">Adicionar Funcionário</label>
                <input type="text" id="funcionario" class="form-control autocomplete-input" placeholder="Selecione um funcionário">
            </div>
        </c:if>

        <div class="form-group">
            <h3>Pessoas Adicionadas</h3>
            <table class="table table-bordered" id="pessoasTable">
                <thead>
                <tr>
                    <th>Nome</th>
                    <th>Data de Nascimento</th>
                    <th>CPF</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="pessoa" items="${pessoas}">
                        <tr data-id="${pessoa.id}">
                            <td>${pessoa.nome}</td>
                            <td>${pessoa.dataNascimento}</td>
                            <td>${pessoa.cpf}</td>
                            <td>
                                <button type="button" class="btn btn-danger remover-pessoa">Remover</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${modo != 'visualizar'}">
            <button type="submit" class="btn btn-success mb-5">Salvar</button>
        </c:if>

        <a href="/" class="btn btn-secondary mb-5">Voltar</a>
    </form>
</div>

<script src="/js/projeto-criar.js"></script>

</body>
</html>
