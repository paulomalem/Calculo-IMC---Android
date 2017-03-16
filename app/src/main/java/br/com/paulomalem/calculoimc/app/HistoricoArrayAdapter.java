package br.com.paulomalem.calculoimc.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.paulomalem.calculoimc.R;
import br.com.paulomalem.calculoimc.dominio.entidades.Imc;

/**
 * Created by paulo on 03/08/2016.
 */
public class HistoricoArrayAdapter extends ArrayAdapter<Imc> {

    private int resource = 0;
    private LayoutInflater inflater;
    private Context context;

    public HistoricoArrayAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View view = null;
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            view = inflater.inflate(resource, parent, false);

            viewHolder.txtData = (TextView)view.findViewById(R.id.txtData);
            viewHolder.txtImc = (TextView)view.findViewById(R.id.txtImc);
            viewHolder.txtPeso = (TextView)view.findViewById(R.id.txtPeso);
            viewHolder.txtAltura = (TextView)view.findViewById(R.id.txtAltura);

            view.setTag(viewHolder);

            convertView = view;

        }else {
            viewHolder = (ViewHolder)convertView.getTag();
            view = convertView;
        }

        Imc imc = getItem(position);

        //SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        //String data = formatador.format(venda.getDataVenda());

        String data = imc.getData_consulta();
        viewHolder.txtData.setText("Data: " + data);

        String vlImc = imc.getResultadoImc();
        vlImc = vlImc.replace(".",",");
        viewHolder.txtImc.setText("IMC: " + vlImc);

        String vlPeso = imc.getPeso();
        vlPeso = vlPeso.replace(".",",");
        viewHolder.txtPeso.setText("Peso: " + vlPeso);

        String vlAltura = imc.getAltura();
        vlAltura = vlAltura.replace(".",",");
        viewHolder.txtAltura.setText("Altura: " + vlAltura);


        return view;
    }

    static class ViewHolder {
        TextView txtData, txtImc, txtPeso, txtAltura;
    }

}