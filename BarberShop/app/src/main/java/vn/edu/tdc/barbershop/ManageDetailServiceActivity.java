package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import vn.edu.tdc.barbershop.entity.Service;

public class DetailServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service);
        EditText edtName = findViewById(R.id.textInputEditName);
        EditText edtPrice = findViewById(R.id.textInputEditPrice);
        EditText edtDes = findViewById(R.id.textInputEditDes);
        ShapeableImageView img = findViewById(R.id.imgEditInput);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Service service = (Service) bundle.get("object_service");
        edtName.setText(service.getName());
        edtPrice.setText(service.getPrice()+"");
        edtDes.setText(service.getDescription());
        Picasso.with(DetailServiceActivity.this ).load(service.getImage()).into(img);
    }
}