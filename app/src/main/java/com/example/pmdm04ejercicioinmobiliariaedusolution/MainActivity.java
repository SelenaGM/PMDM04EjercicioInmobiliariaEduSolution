package com.example.pmdm04ejercicioinmobiliariaedusolution;

import android.content.Intent;
import android.os.Bundle;

import com.example.pmdm04ejercicioinmobiliariaedusolution.configuraciones.Constantes;
import com.example.pmdm04ejercicioinmobiliariaedusolution.modelos.Inmueble;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pmdm04ejercicioinmobiliariaedusolution.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Inmueble> inmueblesList;
    private ActivityResultLauncher<Intent> addInmuebleLauncher;
    private ActivityResultLauncher<Intent> editInmuebleLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inmueblesList = new ArrayList<>();
        inicializaLaunchers();


        setSupportActionBar(binding.toolbar);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInmuebleLauncher.launch(new Intent(MainActivity.this,AddInmuebleActivity.class));
            }
        });
    }

    private void inicializaLaunchers() {
        addInmuebleLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            if(result.getData() != null){
                                if(result.getData().getExtras() != null){
                                    //NO SE DEBEN PONER LAS KEYS ESCRITOS POR POSIBLES FALLOS, MEJOR CREAR UNA CONSTANTE EN UNA CLASE DONDE LOS GENERAS
                                    Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable(Constantes.INMUEBLE);
                                    if(inmueble!= null){
                                        inmueblesList.add(inmueble);
                                        mostrarInmuebles();

                                    }else{
                                        Toast.makeText(MainActivity.this, "NO HAY INMUEBLE EN EL BUNDLE", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(MainActivity.this, "NO HAY BUNDLE", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this, "SET RESULT NO TIENE INTENT", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "VENTANA CANCELADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

        );
        editInmuebleLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    if ( result.getData() != null && result.getData().getExtras() != null){
                        //sacamos el inmueble
                        Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable(Constantes.INMUEBLE);
                        //sacamos la posicion donde hay que meterlo del bundle.
                        int posicion = result.getData().getExtras().getInt(Constantes.POSICION);
                        if(inmueble!= null) {
                            //añadimos en el array la posicion y el inmueble
                            inmueblesList.set(posicion, inmueble);
                            mostrarInmuebles();

                        }else{
                            inmueblesList.remove(posicion);
                            mostrarInmuebles();
                        }
                        }else{

                    }
                }
            }
        });
    }

    private void mostrarInmuebles() {
        binding.contentMain.contenedor.removeAllViews();

        for (int i = 0; i < inmueblesList.size(); i++) {
            Inmueble inmueble = inmueblesList.get(i);
            View inmuebleView = LayoutInflater.from(MainActivity.this).inflate(R.layout.inmueble_model_view, null);
            TextView lblDireccion = inmuebleView.findViewById(R.id.lblDireccionInmuebleModel);
            TextView lblNumero = inmuebleView.findViewById(R.id.lblNumeroInmuebleModel);
            TextView lblProvincia = inmuebleView.findViewById(R.id.lblProvinciaInmuebleModel);
            TextView lblCiudad = inmuebleView.findViewById(R.id.lblCiudadInmuebleModel);
            RatingBar rbValoracion = inmuebleView.findViewById(R.id.rbValoracionInmuebleModel);

            //como la i no puede ser constante porque se romperia, creamos una variable para guardar la i
            final int finalI = i;
            inmuebleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, EditInmuebleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constantes.INMUEBLE, inmueble);
                    bundle.putInt(Constantes.POSICION, finalI);
                    intent.putExtras(bundle);
                    editInmuebleLauncher.launch(intent);
                }
            });


            lblDireccion.setText(inmueble.getDireccion());
            lblCiudad.setText(inmueble.getCiudad());
            lblProvincia.setText(inmueble.getProvincia());
            lblNumero.setText(String.valueOf(inmueble.getNumero()));
            rbValoracion.setRating(inmueble.getValoracion());

            binding.contentMain.contenedor.addView(inmuebleView);


        }

    }


}