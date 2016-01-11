package com.example.andrea.discodb;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private MyDBAdapter dbAdapter;
    EditText id_alumn,id_prof;
    Button borrar, baseDatos;
    ListView lista;
    Spinner spinner_1,spinner_2,spinner_3;
    String PERSONA;
    String CICLO;
    String CURSO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        id_alumn = (EditText) findViewById(R.id.edit_idalum);
        id_prof = (EditText) findViewById(R.id.editidprof);
        borrar = (Button) findViewById(R.id.bReg);
        baseDatos = (Button) findViewById(R.id.bBD);
        lista = (ListView) findViewById(R.id.lista);
        spinner_1 = (Spinner) findViewById(R.id.sp_todos);
        spinner_2 = (Spinner) findViewById(R.id.sp_ciclo);
        spinner_3 = (Spinner) findViewById(R.id.sp_curso);



        dbAdapter = new MyDBAdapter(this);
        dbAdapter.open();

        dbAdapter.insertarEstudiante("Luis", 21, "DAM", "primero", 9.0);
        dbAdapter.insertarEstudiante("Jose", 28, "DAW","segundo", 8.0);
        dbAdapter.insertarProfesor("Oscar", 41, "DAM", "segundo", 233);
        dbAdapter.insertarProfesor("Manel", 55, "DAW", "primero", 112);

        //SPINNER 1
        String[] todos = new String[]{"Todos","Estudiantes","Profesores"};

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,todos);

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner_1.setAdapter(adaptador);

        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else if (position == 1) {
                    PERSONA="Estudiantes";
                    ArrayList<String> estudiantes = dbAdapter.recuperarEstudiantes();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_checked, estudiantes);
                    lista.setAdapter(adapter);
                } else {
                    PERSONA="Profesores";
                    ArrayList<String> profesores = dbAdapter.recuperarProfesores();
                    ArrayAdapter<String> adapt = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_checked, profesores);
                    lista.setAdapter(adapt);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //SPINNER 2
        String[] ciclo = new String[]{"ciclo","DAM","DAW"};

        ArrayAdapter<String> adaptad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ciclo);

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner_2.setAdapter(adaptad);

        spinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else if(position==1){
                    CICLO="DAM";
                    //Toast.makeText(getApplicationContext(), "PRUEBAAA", Toast.LENGTH_LONG).show();
                    ArrayList<String> ciclo = dbAdapter.recuperarCiclo(CICLO);
                    ArrayAdapter<String> adapte = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_checked, ciclo);
                    lista.setAdapter(adapte);
                }else{
                    CICLO="DAW";
                    ArrayList<String> curso = dbAdapter.recuperarCiclo(CICLO);
                    ArrayAdapter<String> adapte = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_checked, curso);
                    lista.setAdapter(adapte);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //SPINNER 3
        String[] curso = new String[]{"curso","primero","segundo"};

        ArrayAdapter<String> adaptader = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,curso);

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner_3.setAdapter(adaptader);

        spinner_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ArrayList<String> cicur = dbAdapter.recuperarCiCur();
                    ArrayAdapter<String> adapte = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_checked, cicur);
                    lista.setAdapter(adapte);
                } else if(position==1){
                    CURSO="primero";
                    ArrayList<String> curso = dbAdapter.recuperarCurso(CURSO);
                    ArrayAdapter<String> adapte = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_checked, curso);
                    lista.setAdapter(adapte);
                }else{
                    CURSO="segundo";
                    ArrayList<String> curso = dbAdapter.recuperarCurso(CURSO);
                    ArrayAdapter<String> adapte = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_checked, curso);
                    lista.setAdapter(adapte);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //seleccionar registro para eliminarlo
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        //Boton para borrar un registro segun su id
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_alumn.getText().toString().length()!=0) {
                    dbAdapter.borrarAlumno(Integer.parseInt(id_alumn.getText().toString()));
                    lista.invalidateViews();

                }else{
                    Toast.makeText(getApplicationContext(),"Pon un id para borrar un alumno",Toast.LENGTH_LONG).show();
                }
                if(id_prof.getText().toString().length()!=0) {
                    dbAdapter.borrarProfesor(Integer.parseInt(id_prof.getText().toString()));
                    lista.invalidateViews();
                }else{
                    Toast.makeText(getApplicationContext(),"Pon un id para borrar un profesor",Toast.LENGTH_LONG).show();
                }
            }
        });

        //Boton para borrar la base de datos
        baseDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dbAdapter.borrarBD();
            }
        });

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.sp_todos) {
            //do this
        }
        else if(spinner.getId() == R.id.sp_ciclo) {
            //do this
        }else if(spinner.getId() == R.id.sp_curso) {
            //do this
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
