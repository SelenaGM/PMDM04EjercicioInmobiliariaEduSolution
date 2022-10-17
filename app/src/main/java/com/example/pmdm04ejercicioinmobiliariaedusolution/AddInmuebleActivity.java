package com.example.pmdm04ejercicioinmobiliariaedusolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pmdm04ejercicioinmobiliariaedusolution.configuraciones.Constantes;
import com.example.pmdm04ejercicioinmobiliariaedusolution.databinding.ActivityAddInmuebleBinding;
import com.example.pmdm04ejercicioinmobiliariaedusolution.databinding.InmuebleModelViewBinding;
import com.example.pmdm04ejercicioinmobiliariaedusolution.modelos.Inmueble;

public class AddInmuebleActivity extends AppCompatActivity {

    private ActivityAddInmuebleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inmueble);
        binding = ActivityAddInmuebleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelarAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnCrearAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inmueble inmueble = crearInmueble();
                if(inmueble!= null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constantes.INMUEBLE, inmueble);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();

                }else{
                    Toast.makeText(AddInmuebleActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private Inmueble crearInmueble() {
        if(binding.txtCiudadAddInmueble.getText().toString().isEmpty() ||
                binding.txtDireccionAddInmueble.getText().toString().isEmpty() ||
                binding.txtCpAddInmueble.getText().toString().isEmpty() ||
                binding.txtProvinciaAddInmueble.getText().toString().isEmpty() ||
                binding.txtNumeroAddInmueble.getText().toString().isEmpty()
            ) {
            return null;
        }

        return new Inmueble(
                binding.txtDireccionAddInmueble.getText().toString(),
                Integer.parseInt(binding.txtNumeroAddInmueble.getText().toString()),
                binding.txtCpAddInmueble.getText().toString(),
                        binding.txtCiudadAddInmueble.getText().toString(),
                        binding.txtProvinciaAddInmueble.getText().toString(),
                        binding.rbValAddInmueble.getRating()
        );

    }
}