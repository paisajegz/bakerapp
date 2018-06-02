package chulo.paisa.com.appadministradora.controller.productos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import chulo.paisa.com.appadministradora.R;
import chulo.paisa.com.appadministradora.controller.categoria.SubirFotoCatActivity;
import chulo.paisa.com.appadministradora.modelo.Categoria;
import chulo.paisa.com.appadministradora.modelo.Producto;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SubirFotoProActivity extends AppCompatActivity {

    private Button btnFoto;
    private Button btnSubirFotoPro;
    private ImageView imageProduct;
    private final int MIS_PERMISOS = 100;
    private static final int SELECT_FILE = 1;
    private final int CAMERA_REQUEST=123;
    private  Bundle parametros;
    private String categoria;
    private Bitmap imagen;
    private StorageReference mStorageRef;
    private Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_foto_pro);
        Typeface face=Typeface.createFromAsset(getAssets(),"news706.ttf");

        btnSubirFotoPro = (Button) findViewById(R.id.btnSubirProduct);
        btnFoto = (Button) findViewById(R.id.btnFoto);
        imageProduct = (ImageView) findViewById(R.id.imageProduct);

        btnSubirFotoPro.setTypeface(face);
        btnFoto.setTypeface(face);

        parametros= getIntent().getExtras();
        categoria = parametros.getString("categoria");
        producto = new Producto();
        producto.setNombre(parametros.getString("producto"));
        producto.setPrecio(parametros.getInt("precio"));
        mStorageRef = FirebaseStorage.getInstance().getReference();
        solicitaPermisosVersionesSuperiores();
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });

        btnSubirFotoPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(SubirFotoProActivity.this);
                dialog.setMessage("subiendo producto");
                dialog.show();
                String g ="";
                StorageReference mountainsRef = mStorageRef.child("producto").child(producto.getNombre()+ ".jpg");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imagen.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("producto");
                        producto.setFoto(taskSnapshot.getDownloadUrl().toString());
                        myRef.child(categoria).child(producto.getNombre()).setValue(producto);
                        dialog.dismiss();
                        Toast.makeText(SubirFotoProActivity.this,"producto subido exitosamente",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bitmap photo= (Bitmap) data.getExtras().get("data");
            Bitmap nuevo=redimensionarImagen(photo,300,300);
            this.imagen=nuevo;
            imageProduct.setImageBitmap(nuevo);
        }

        if(requestCode == SELECT_FILE && resultCode == Activity.RESULT_OK){
            selectedImage = data.getData();
            String selectedPath=selectedImage.getPath();

            if (selectedPath != null) {
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(
                            selectedImage);
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                Bitmap nuevo=redimensionarImagen(bmp,300,300);
                this.imagen=nuevo;
                imageProduct.setImageBitmap(nuevo);

            }
        }
    }


    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        if(ancho>anchoNuevo || alto>altoNuevo){
            float escalaAncho=anchoNuevo/ancho;
            float escalaAlto= altoNuevo/alto;

            Matrix matrix=new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{
            return bitmap;
        }


    }

    private void mostrarDialogOpciones() {
        final CharSequence[] opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){

                    Intent inte= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(inte,CAMERA_REQUEST);

                }else{
                    if (opciones[i].equals("Elegir de Galeria")){

                        abrirGaleria();

                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }
    public void abrirGaleria(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);
    }

    private boolean solicitaPermisosVersionesSuperiores() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){//validamos si estamos en android menor a 6 para no buscar los permisos
            return true;
        }

        //validamos si los permisos ya fueron aceptados

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&& checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED){
            return true;
        }


        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)||(shouldShowRequestPermissionRationale(CAMERA)))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MIS_PERMISOS);
        }

        return false;//implementamos el que procesa el evento dependiendo de lo que se defina aqui
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MIS_PERMISOS){
            if(grantResults.length==2 && grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){//el dos representa los 2 permisos
                Toast.makeText(getApplicationContext(),"Permisos aceptados", Toast.LENGTH_SHORT);
                btnFoto.setEnabled(true);
            }
        }else{
            solicitarPermisosManual();
        }
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }
    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(this);//estamos en fragment
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }
}