$(document).ready(() => {

    $("#funcionario").autocomplete({
        source: (request, response) => {
            $.ajax({
                url: "/api/pessoa/autocomplete/funcionarios",
                data: {
                    nome: request.term
                },
                success: (data) => {
                    response($.map(data, (pessoa) => {
                        return {
                            label: pessoa.nome,
                            value: JSON.stringify(pessoa),
                            id: pessoa.id
                        };
                    }));
                }
            });
        },
        select: (event, ui) => {
            $("#funcionario").val(ui.item.label);

            const pessoa = JSON.parse(ui.item.value);

            // Verificar se a pessoa já existe pelo CPF
            let pessoaExistente = false;
            $('#pessoasTable tbody tr').each(function () {
                const cpfExistente = $(this).find('td:eq(2)').text();
                if (cpfExistente === pessoa.cpf) {
                    pessoaExistente = true;
                    alert('Pessoa com esse CPF já foi adicionada!');
                    $("#funcionario").val('');
                    return false;
                }
            });

            // Se a pessoa não existir, adicionar na tabela
            if (!pessoaExistente) {
                const novaLinha = `
                <tr data-id="${pessoa.id}">
                    <td>` + pessoa.nome + `</td>
                    <td>` + pessoa.dataNascimento + `</td>
                    <td>` + pessoa.cpf + `</td>
                    <td><button class="btn btn-danger remover-pessoa" >Remover</button></td>
                </tr>`;
                $('#pessoasTable tbody').append(novaLinha);
            }

            $("#funcionario").val('');
            return false;
        }
    });

    $(document).on('click', '.remover-pessoa', function () {
        $(this).closest('tr').remove()
    });

    $("#gerenteNome").autocomplete({
        source: (request, response) => {
            $.ajax({
                url: "/api/pessoa/autocomplete/gerentes",
                data: {
                    nome: request.term
                },
                success: (data) => {
                    response($.map(data, (pessoa) => {
                        return {
                            label: pessoa.nome,
                            value: pessoa.nome,
                            id: pessoa.id
                        };
                    }));
                }
            });
        },
        select: (event, ui) => {
            $("#gerenteNome").val(ui.item.value);  // Nome da pessoa selecionada
            $("#gerenteId").val(ui.item.id);   // ID da pessoa para enviar no form
            return false;
        }
    });
    $('#form').submit((event) => {
        event.preventDefault(); // Previne o redirecionamento padrão do submit

        const id = $('#id').val();

        let pessoas = [];

        $('#pessoasTable tbody tr').each(function () {
            const pessoaId = $(this).attr('data-id');
            pessoas.push(pessoaId);
        });

        // Pegar os dados do formulário
        const formData = {
            id: id.length > 0 ? id : null,
            nome: $('#nome').val(),
            dataInicio: $('#dataInicio').val(),
            dataPrevisaoFim: $('#dataPrevisaoFim').val(),
            dataFim: $('#dataFim').val(),
            descricao: $('#descricao').val(),
            status: $('#status').val(),
            risco: $('#risco').val(),
            orcamento: $('#orcamento').val(),
            gerenteNome: $('#gerenteNome').val(),
            gerenteId: $('#gerenteId').val(),
            pessoas: pessoas,
        };

        // Fazer o envio dos dados via AJAX
        $.ajax({
            type: id !== '' ? 'PUT' : 'POST',
            contentType: "application/x-www-form-urlencoded",
            url: '/api/projetos',  // URL da API para onde os dados serão enviados
            data: formData,

            success: () => window.location.href = "/",
            error: (error) => alert(error.responseJSON.message)
        });
    });
});