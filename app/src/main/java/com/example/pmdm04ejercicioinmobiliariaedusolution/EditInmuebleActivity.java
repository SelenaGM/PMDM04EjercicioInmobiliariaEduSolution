package com.example.pmdm04ejercicioinmobiliariaedusolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pmdm04ejercicioinmobiliariaedusolution.configuraciones.Constantes;
import com.example.pmdm04ejercicioinmobiliariaedusolution.databinding.ActivityEditInmuebleBinding;
import com.example.pmdm04ejercicioinmobiliariaedusolution.modelos.Inmueble;

public class EditInmuebleActivity extends AppCompatActivity {

    private ActivityEditInmuebleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditInmuebleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intentMain = getIntent();
        Bundle bundleMain = intentMain.getExtras();
        Inmueble inmuebleEdit =(Inmueble) bundleMain.getSerializable(Constantes.INMUEBLE);

        Log.d("INMU:", inmuebleEdit.toString());

        binding.txtDireccionEditInmueble.setText(inmuebleEdit.getDireccion());
        binding.txtNumeroEditInmueble.setText(String.valueOf(inmuebleEdit.getNumero()));
        binding.txtCpEditInmueble.setText(inmuebleEdit.getCp());
        binding.txtCiudadEditInmueble.setText(inmuebleEdit.getCiudad());
        binding.txtProvinciaEditInmueble.setText(inmuebleEdit.getProvincia());
        binding.rbValEditInmueble.setRating(inmuebleEdit.getValoracion());


        binding.btnActualizarEditInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inmueble inmueble = crearInmueble();
                if(inmueble!= null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constantes.INMUEBLE, inmueble);
                    bundle.putInt(Constantes.POSICION, bundle.getInt(Constantes.POSICION));
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();

                }else{
                    Toast.makeText(EditInmuebleActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnEliminarEditInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //En nuestra lógica si no nos devuelve un inmueble, significará que está eliminado
                Bundle bundle = new Bundle();
                bundle.putInt(Constantes.POSICION, bundle.getInt(Constantes.POSICION));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }

    private Inmueble crearInmueble() {
        if(binding.txtCiudadEditInmueble.getText().toString().isEmpty() ||
                binding.txtDireccionEditInmueble.getText().toString().isEmpty() ||
                binding.txtCpEditInmueble.getText().toString().isEmpty() ||
                binding.txtProvinciaEditInmueble.getText().toString().isEmpty() ||
                binding.txtNumeroEditInmueble.getText().toString().isEmpty()
        ) {
            return null;
        }

        return new Inmueble(
                binding.txtDireccionEditInmueble.getText().toString(),
                Integer.parseInt(binding.txtNumeroEditInmueble.getText().toString()),
                binding.txtCpEditInmueble.getText().toString(),
                binding.txtCiudadEditInmueble.getText().toString(),
                binding.txtProvinciaEditInmueble.getText().toString(),
                binding.rbValEditInmueble.getRating()
        );

    }
}