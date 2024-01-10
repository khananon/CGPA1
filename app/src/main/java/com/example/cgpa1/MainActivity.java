package com.example.cgpa1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cgpa1.Calculate;
import com.example.cgpa1.R;
import com.example.cgpa1.RvAdapter;
import com.example.cgpa1.SubjectModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<SubjectModel> arrSub = new ArrayList<>();
    RvAdapter adapter;
    RecyclerView Rv;
    Button Calculatebtn;
    FloatingActionButton btnOpenDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Rv = findViewById(R.id.Rview);
        btnOpenDlg = findViewById(R.id.floatingActionButton2);

        btnOpenDlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_update);
                EditText edName = dialog.findViewById(R.id.ubjectNameD);
                EditText edCredit = dialog.findViewById(R.id.CreditD);
                EditText edGrade = dialog.findViewById(R.id.GradeD);
                Button btnAction = dialog.findViewById(R.id.BtnD);
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String SubjectName = " ", GradeM = " ", CreditM = " ";
                        if (!edName.getText().toString().equals("")) {
                            SubjectName = edName.getText().toString();
                        } else {
                            Toast.makeText(MainActivity.this, "Please Enter Subject Name", Toast.LENGTH_SHORT).show();
                        }
                        if (!edCredit.getText().toString().equals("")) {
                            CreditM = edCredit.getText().toString();
                        } else {
                            Toast.makeText(MainActivity.this, "Please Enter Credit", Toast.LENGTH_SHORT).show();
                        }
                        if (!edGrade.getText().toString().equals("")) {
                            GradeM = edGrade.getText().toString();
                        } else {
                            Toast.makeText(MainActivity.this, "Please Enter Grade", Toast.LENGTH_SHORT).show();
                        }
                        arrSub.add(new SubjectModel(SubjectName, CreditM, GradeM));
                        adapter.notifyItemInserted(arrSub.size() - 1);
                        Rv.scrollToPosition(arrSub.size() - 1);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        Calculatebtn = findViewById(R.id.Calculatebutton);
        Calculatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double sgpa = calculateSGPA();
                Intent intent = new Intent(MainActivity.this, Calculate.class);
                intent.putExtra("SGPA", sgpa);
                startActivity(intent);
            }
        });

        Rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RvAdapter(this, arrSub);
        Rv.setAdapter(adapter);
    }

    private double calculateSGPA() {
        double totalCreditPoints = 0.0;
        double totalCredits = 0.0;

        for (SubjectModel subject : arrSub) {
            double credit = Double.parseDouble(subject.Credit);
            double grade = convertGradeToNumeric(subject.Grade);

            totalCreditPoints += credit * grade;
            totalCredits += credit;
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        return totalCreditPoints / totalCredits;
    }

    private double convertGradeToNumeric(String grade) {
        switch (grade) {
            case "A+":
                return 10.0;
            case "A":
                return 9.0;
            case "B+":
                return 8.0;
            case "B":
                return 7.0;
            case "C+":
                return 6.0;
            case "C":
                return 5.0;
            case "D":
                return 4.0;
            default:
                return 0.0;
        }
    }
}
