package com.example.marvelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class PerfilFragment extends Fragment {

    private TextView textViewNombre, textViewCorreo, textViewContrasena, textViewFechaNacimiento;
    private Button buttonCerrarSesion;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        textViewNombre = view.findViewById(R.id.textViewNombre);
        textViewCorreo = view.findViewById(R.id.textViewCorreo);
        textViewContrasena = view.findViewById(R.id.textViewContrasena);
        textViewFechaNacimiento = view.findViewById(R.id.textViewFechaNacimiento);
        buttonCerrarSesion = view.findViewById(R.id.buttonCerrarSesion);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadUserData();

        buttonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        return view;
    }

    private void loadUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DocumentReference docRef = db.collection("users").document(userId);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        textViewNombre.setText(document.getString("nombre"));
                        textViewCorreo.setText(document.getString("email"));
                        textViewFechaNacimiento.setText(document.getString("fechaNacimiento"));
                        // For security, it's not recommended to store and display the password
                        textViewContrasena.setText("********");
                    } else {
                        Toast.makeText(getContext(), "No se encontraron datos del usuario.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error al obtener datos del usuario.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void signOut() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();

        mAuth.signOut();

        Intent intent = new Intent(getActivity(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
