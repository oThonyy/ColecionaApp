package br.thony.fateczl.colecionaapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.thony.fateczl.colecionaapp.controller.ColecaoController;
import br.thony.fateczl.colecionaapp.controller.ItemController;
import br.thony.fateczl.colecionaapp.model.Colecao;
import br.thony.fateczl.colecionaapp.model.Item;
import br.thony.fateczl.colecionaapp.persistance.ColecaoDao;
import br.thony.fateczl.colecionaapp.persistance.ItemDao;


public class ItemFragment extends Fragment {

    private View view;

    private EditText etIdItem, etNomeItem, etDescricaoItem, etCondicaoItem, etValorEstimadoItem, etDataAquisicaoItem;

    private Spinner spColecaoItem;

    private Button btnInsereItem, btnEditaItem, btnExcluiItem, btnListaItem, btnBuscarItem;

    private TextView tvListarItem;

    private ItemController iCont;

    private ColecaoController cCont;

    private List<Colecao> colecoes;

    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item, container, false);

        etIdItem = view.findViewById(R.id.etIdItem);
        etNomeItem = view.findViewById(R.id.etNomeItem);
        etDescricaoItem = view.findViewById(R.id.etDescricaoItem);
        etCondicaoItem = view.findViewById(R.id.etCondicaoItem);
        etValorEstimadoItem = view.findViewById(R.id.etValorEstimadoItem);
        etDataAquisicaoItem = view.findViewById(R.id.etDataAquisicaoItem);

        btnInsereItem = view.findViewById(R.id.btnInsereItem);
        btnEditaItem = view.findViewById(R.id.btnEditaItem);
        btnExcluiItem = view.findViewById(R.id.btnExcluiItem);
        btnListaItem = view.findViewById(R.id.btnListaItem);
        btnBuscarItem = view.findViewById(R.id.bntBuscarItem);

        tvListarItem = view.findViewById(R.id.tvListarItem);
        tvListarItem.setMovementMethod(new ScrollingMovementMethod());

        spColecaoItem = view.findViewById(R.id.spColecaoItem);

        iCont = new ItemController(new ItemDao(view.getContext()));
        cCont = new ColecaoController(new ColecaoDao(view.getContext()));
        preencheSpinner();

        btnInsereItem.setOnClickListener(op -> acaoInserir());
        btnEditaItem.setOnClickListener(op -> acaoEditar());
        btnExcluiItem.setOnClickListener(op -> acaoExcluir());
        btnBuscarItem.setOnClickListener(op -> acaoBuscar());
        btnListaItem.setOnClickListener(op -> acaoListar());

        return view;
    }

    private void acaoInserir() {
        int spPos = spColecaoItem.getSelectedItemPosition();
        if (spPos > 0) {
            Item item = montaItem();
            try {
                iCont.inserir(item);
                Toast.makeText(view.getContext(), "Item Inserido com Sucesso!",
                        Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            limpaCampos();
        } else {
            Toast.makeText(view.getContext(), "Selecione uma Coleção",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void acaoEditar() {
        int spPos = spColecaoItem.getSelectedItemPosition();
        if (spPos > 0) {
            Item item = montaItem();
            try {
                iCont.editar(item);
                Toast.makeText(view.getContext(), "Item Editado com Sucesso!",
                        Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            limpaCampos();
        } else {
            Toast.makeText(view.getContext(), "Selecione uma Coleção",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
        Item item = montaItem();
        try {
            iCont.excluir(item);
            Toast.makeText(view.getContext(), "Item Excluído com Sucesso!",
                    Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoBuscar() {
        Item item = montaItem();
        try {
            colecoes = cCont.listar();
            item = iCont.buscar(item);
            if (item.getNome() != null) {
                preencheCampos(item);
            } else {
                Toast.makeText(view.getContext(), "Item não encontrado",
                        Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Item> itens = iCont.listar();
            StringBuffer buffer = new StringBuffer();
            for (Item i : itens) {
                buffer.append(i.toString() + "\n");
            }
            tvListarItem.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void preencheSpinner() {
        Colecao c0 = new Colecao();
        c0.setId(0);
        c0.setNome("Selecione uma Coleção");
        c0.setDescricao("");
        c0.setCategoria("");
        c0.setDataCriacao("");

        try {
            colecoes = cCont.listar();
            colecoes.add(0, c0);

            ArrayAdapter ad = new ArrayAdapter(view.getContext(),
                    android.R.layout.simple_spinner_item,
                    colecoes);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spColecaoItem.setAdapter(ad);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private Item montaItem() {
        Item i = new Item();
        i.setId(Integer.parseInt(etIdItem.getText().toString()));
        i.setNome(etNomeItem.getText().toString());
        i.setDescricao(etDescricaoItem.getText().toString());
        i.setCondicao(etCondicaoItem.getText().toString());
        i.setValorEstimado(etValorEstimadoItem.getText().toString());
        i.setDataAquisicao(etDataAquisicaoItem.getText().toString());
        i.setColecao((Colecao) spColecaoItem.getSelectedItem());

        return i;
    }

    private void limpaCampos() {
        etIdItem.setText("");
        etNomeItem.setText("");
        etDescricaoItem.setText("");
        etCondicaoItem.setText("");
        etValorEstimadoItem.setText("");
        etDataAquisicaoItem.setText("");
        spColecaoItem.setSelection(0);
    }

    public void preencheCampos(Item i) {
        etIdItem.setText(String.valueOf(i.getId()));
        etNomeItem.setText(i.getNome());
        etDescricaoItem.setText(i.getDescricao());
        etCondicaoItem.setText(i.getCondicao());
        etValorEstimadoItem.setText(i.getValorEstimado());
        etDataAquisicaoItem.setText(i.getDataAquisicao());

        int cont = 1;
        for (Colecao c : colecoes) {
            if (c.getId() == i.getColecao().getId()) {
                spColecaoItem.setSelection(cont);
                //break;
            } else {
                cont++;
            }
        }
        if (cont > colecoes.size()) {
            spColecaoItem.setSelection(0);
        }
    }
}