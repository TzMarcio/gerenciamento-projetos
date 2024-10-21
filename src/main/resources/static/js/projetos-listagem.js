function excluirProjeto(id) {
    if (confirm('Tem certeza que deseja excluir este projeto?')) {
        $.ajax({
            url: '/api/projetos/' + id,
            type: 'DELETE',
            success: function(result) {
                $('#projeto-' + id).remove();
                alert('Projeto excluído com sucesso.');
            },
            error: function(err) {
                alert('Erro ao excluir o projeto.');
            }
        });
    }
}