package br.thony.fateczl.colecionaapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.thony.fateczl.colecionaapp.controller.ColecaoController;
import br.thony.fateczl.colecionaapp.model.Colecao;
import br.thony.fateczl.colecionaapp.persistance.ColecaoDao;

public class ColecaoFragment extends Fragment {

    private View view;

    private EditText etIdColecao, etNomeColecao, etDescricaoColecao, etCategoriaColecao, etDataCriacaoColecao;

    private Button btnInsereColecao, btnEditaColecao, btnExcluiColecao, btnListaColecao, btnBuscarColecao;

    private TextView tvListarColecao;

    private ColecaoController cCont;

    public ColecaoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_colecao, container, false);

        etIdColecao = view.findViewById(R.id.etIdColecao);
        etNomeColecao = view.findViewById(R.id.etNomeColecao);
        etDescricaoColecao = view.findViewById(R.id.etDescricaoColecao);
        etCategoriaColecao = view.findViewById(R.id.etCategoriaColecao);
        etDataCriacaoColecao = view.findViewById(R.id.etDataCriacaoColecao);

        btnInsereColecao = view.findViewById(R.id.btnInsereColecao);
        btnEditaColecao = view.findViewById(R.id.btnEditaColecao);
        btnExcluiColecao = view.findViewById(R.id.btnExcluiColecao);
        btnListaColecao = view.findViewById(R.id.btnListaColecao);
        btnBuscarColecao = view.findViewById(R.id.bntBuscarColecao);

        tvListarColecao = view.findViewById(R.id.tvListarColecao);
        tvListarColecao.setMovementMethod(new ScrollingMovementMethod());

        cCont = new ColecaoController(new ColecaoDao(view.getContext()));

        btnInsereColecao.setOnClickListener(op -> acaoInserir());
        btnEditaColecao.setOnClickListener(op -> acaoEditar());
        btnExcluiColecao.setOnClickListener(op -> acaoExcluir());
        btnBuscarColecao.setOnClickListener(op -> acaoBuscar());
        btnListaColecao.setOnClickListener(op -> acaoListar());

        cCont = new ColecaoController(new ColecaoDao(view.getContext()));

        return view;
    }

    private void acaoInserir() {
        Colecao colecao = montaColecao();
        try {
            cCont.inserir(colecao);
            Toast.makeText(view.getContext(), "Coleção Inserida com Sucesso!",
                    Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoEditar() {
        Colecao colecao = montaColecao();
        try {
            cCont.editar(colecao);
            Toast.makeText(view.getContext(), "Coleção Editada com Sucesso!",
                    Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoExcluir() {
        Colecao colecao = montaColecao();
        try {
            cCont.excluir(colecao);
            Toast.makeText(view.getContext(), "Coleção Excluída com Sucesso!",
                    Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoBuscar() {
        Colecao colecao = montaColecao();
        try {
            colecao = cCont.buscar(colecao);
            if (colecao.getNome() != null) {
                preencheCampos(colecao);
            } else {
                Toast.makeText(view.getContext(), "Coleção não encontrada",
                        Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Colecao> colecoes = cCont.listar();
            StringBuffer buffer = new StringBuffer();
            for (Colecao c : colecoes) {
                buffer.append(c.toString() + "\n");
            }
            tvListarColecao.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Colecao montaColecao() {
        Colecao c = new Colecao();
        c.setId(Integer.parseInt(etIdColecao.getText().toString()));
        c.setNome(etNomeColecao.getText().toString());
        c.setDescricao(etDescricaoColecao.getText().toString());
        c.setCategoria(etCategoriaColecao.getText().toString());
        c.setDataCriacao(etDataCriacaoColecao.getText().toString());

        return c;
    }

    private void preencheCampos(Colecao c) {
        etIdColecao.setText(String.valueOf(c.getId()));
        etNomeColecao.setText(c.getNome());
        etDescricaoColecao.setText(c.getDescricao());
        etCategoriaColecao.setText(c.getCategoria());
        etDataCriacaoColecao.setText(c.getDataCriacao());
    }

    private void limpaCampos() {
        etIdColecao.setText("");
        etNomeColecao.setText("");
        etDescricaoColecao.setText("");
        etCategoriaColecao.setText("");
        etDataCriacaoColecao.setText("");
    }
}