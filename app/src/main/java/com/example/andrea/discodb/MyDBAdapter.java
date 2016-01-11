package com.example.andrea.discodb;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Andrea on 02/12/2015.
 */
public class MyDBAdapter {

    //Definiciones y constantes
    private static final String DATABASE_NAME= "dbcolegio.db";
    private static final String TABLE_ESTUDIANTES = "estudiantes";
    private static final String TABLE_PROFESORES = "profesores";
    private static final int DATABASE_VERSION = 1;

    //nombre de los parametros para insertarlos en las tablas
    private static final String NOMBRE = "nombre";
    private static final String EDAD = "edad";
    private static final String CICLO = "ciclo";
    private static final String CURSO = "curso";
    private static final String NOTA = "nota";
    private static final String DESPACHO = "despacho";


    private static final String DATABASE_CREATE = "CREATE TABLE "+TABLE_ESTUDIANTES+" (_id integer primary key autoincrement, nombre text, edad integer, ciclo text,curso text, nota double);";
    private static final String DATABASE_CREATE2 = "CREATE TABLE "+TABLE_PROFESORES+" (_id integer primary key autoincrement, nombre text, edad integer, ciclo text,curso text, despacho integer);";
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS "+TABLE_ESTUDIANTES+";";
    private static final String DATABASE_DROP2 = "DROP TABLE IF EXISTS "+TABLE_PROFESORES+";";
    //private static final String DROP_REGISTRO_ALUMNO = "DELETE FROM TABLE "+TABLE_ESTUDIANTES+" WHERE id=?;";

    // Contexto de la aplicación que usa la base de datos
    private final Context context;
    // Clase SQLiteOpenHelper para crear/actualizar la base de datos
    private MyDbHelper dbHelper;
    // Instancia de la base de datos
    private SQLiteDatabase db;

    public MyDBAdapter (Context c){
        context = c;
        dbHelper = new MyDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

        //OJO open();
    }

    public void open(){

        try{
            db = dbHelper.getWritableDatabase();
        }catch(SQLiteException e){
            db = dbHelper.getReadableDatabase();
        }

    }

    /**
     * Insercion de estudiantes en la BD
     * @param n
     * @param e
     * @param c
     * @param cr
     * @param nt
     */
    public void insertarEstudiante(String n, int e,String c, String cr,double nt){
        //Creamos un nuevo registro de valores a insertar
        ContentValues newValues = new ContentValues();
        //Asignamos los valores de cada campo
        newValues.put(NOMBRE,n);
        newValues.put(EDAD,e);
        newValues.put(CICLO,c);
        newValues.put(CURSO,cr);
        newValues.put(NOTA,nt);

        //el insert se hace sobre la tabla no sobre la base de datos
        db.insert(TABLE_ESTUDIANTES,null,newValues);
    }

    /**
     * Metodo que inserta registros de profesores en la BD
     * @param n
     * @param e
     * @param c
     * @param cr
     * @param d
     */
    public void insertarProfesor(String n, int e,String c, String cr,int d){
        //Creamos un nuevo registro de valores a insertar
        ContentValues newValues = new ContentValues();
        //Asignamos los valores de cada campo
        newValues.put(NOMBRE,n);
        newValues.put(EDAD, e);
        newValues.put(CICLO,c);
        newValues.put(CURSO,cr);
        newValues.put(DESPACHO,d);

        //el insert se hace sobre la tabla no sobre la base de datos
        db.insert(TABLE_PROFESORES,null,newValues);
    }

    /**
     * Recuperar los datos en una lista, estudiantes
     * @return
     */
    public ArrayList<String> recuperarEstudiantes(){
        ArrayList<String> estudiantes = new ArrayList<String>();
        //Recuperamos en un cursor la consulta realizada
        Cursor cursor = db.query(TABLE_ESTUDIANTES,null,null,null,null,null,null);
        //Recorremos el cursor
        if (cursor != null && cursor.moveToFirst()){
            do{
                estudiantes.add(cursor.getString(0)+" "+cursor.getString(1));
            }while (cursor.moveToNext());
        }
        return estudiantes;
    }

    /**
     * Recuperar los datos en una lista, profesores
     * @return
     */
    public ArrayList<String> recuperarProfesores(){
        ArrayList<String> profesores = new ArrayList<String>();
        //Recuperamos en un cursor la consulta realizada
        Cursor cursor = db.query(TABLE_PROFESORES,null,null,null,null,null,null);
        //Recorremos el cursor
        if (cursor != null && cursor.moveToFirst()){
            do{
                profesores.add(cursor.getString(0)+" "+cursor.getString(1));
            }while (cursor.moveToNext());
        }
        return profesores;
    }

    /**
     * Metodo que recupera todos los alumnos y profesores
     * @return
     */
    public ArrayList<String> recuperarTodos(){
        ArrayList<String> curso = new ArrayList<String>();
        //Recuperamos en un cursor la consulta realizada
        Cursor cursor = db.query(TABLE_PROFESORES,null,null,null,null,null,null);
        Cursor cur = db.query(TABLE_ESTUDIANTES,null,null,null,null,null,null);
        //Recorremos el cursor
        if (cursor != null && cursor.moveToFirst()){
            do{
                curso.add(cursor.getString(0)+" "+cursor.getString(1));
            }while (cursor.moveToNext());
        }

        if (cur != null && cur.moveToFirst()){
            do{
                curso.add(cur.getString(0)+" "+cur.getString(1));
            }while (cur.moveToNext());
        }
        return curso;
    }

    /**
     * Metodo que recupera el ciclo de profesores y alumnos
     * @return
     */
    public ArrayList<String> recuperarCiclo(String CICLO){
        ArrayList<String> ciclo = new ArrayList<String>();
        //Recuperamos en un cursor la consulta realizada
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROFESORES +" WHERE ciclo = '"+CICLO+"';", null);

        //Recorremos el cursor
        Toast toast2 = Toast.makeText(context, "Voy a entrar" + cursor.getCount(), Toast.LENGTH_LONG);
        toast2.show();
        if (cursor != null && cursor.moveToFirst() ){
            Toast toast = Toast.makeText(context, "He entrado ", Toast.LENGTH_LONG);
            toast.show();

            do{
                ciclo.add(cursor.getString(0)+" "+cursor.getString(1));

            }while (cursor.moveToNext());
        }

        /*if (c != null && c.moveToFirst()){
            do{
                ciclo.add(c.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(3));
            }while (c.moveToFirst());
        }*/

        return ciclo;
    }

    public ArrayList<String> recuperarCurso(String CURSO){
        ArrayList<String> curso = new ArrayList<String>();
        //Recuperamos en un cursor la consulta realizada
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROFESORES +" WHERE curso = '"+CURSO+"';", null);
        //Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ESTUDIANTES +" WHERE curso = '"+CURSO+"';", null);
        //Recorremos el cursor
        if (cursor != null && cursor.moveToFirst() ){
            do{
                curso.add(cursor.getString(0)+" "+cursor.getString(1));

            }while (cursor.moveToNext());
        }

        /*if (c != null && c.moveToFirst()){
            do{
                curso.add(c.getString(0)+" "+cursor.getString(1));
            } while (c.moveToFirst());
        }*/

        return curso;
    }


    /**
     * Metodo que recupera el ciclo y el curso de los profesores y alumnos
     * @return
     */
    public ArrayList<String> recuperarCiCur(){
        ArrayList<String> cicur = new ArrayList<String>();
        //Recuperamos en un cursor la consulta realizada
        Cursor cursor = db.query(TABLE_PROFESORES,null,null,null,null,null,null);
        Cursor cur = db.query(TABLE_ESTUDIANTES,null,null,null,null,null,null);
        //Recorremos el cursor
        if (cursor != null && cursor.moveToFirst() && cur != null && cur.moveToFirst()){
            do{
                cicur.add(cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(3)+" "+cur.getString(4));
                cicur.add(cur.getString(0)+" "+cur.getString(1)+" "+cursor.getString(3)+" "+cur.getString(4));
            }while (cursor.moveToNext() && cur.moveToNext());
        }

        /*if (cur != null && cur.moveToFirst()){
            do{
                cicur.add(cur.getString(0)+" "+cur.getString(1)+" "+" "+cur.getString(4));
            }while (cur.moveToNext());
        }*/
        return cicur;
    }


    /**
     * Metodos para borrar registros en  la base de datos
     * Borrar alumnos
     * @param i
     */
    public void borrarAlumno(int i){
        db.delete(TABLE_ESTUDIANTES, "_id = " + i, null);
    }

    /**
     * Borrar profesores
     * @param i
     */
    public void borrarProfesor(int i){
        db.delete(TABLE_PROFESORES, "_id = " + i, null);
    }

    public void borrarBD(){
        context.deleteDatabase(DATABASE_NAME);
    }

    /**
     * Pone en funcionamiento todos los metodos que van a interaccionar con la DB
     */
    private static class MyDbHelper extends SQLiteOpenHelper {

        public MyDbHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DATABASE_DROP);
            db.execSQL(DATABASE_DROP2);
            onCreate(db);
        }


    }
}
