package com.example.service;

import java.util.List;

import com.example.model.FakultasModel;
import com.example.model.ProdiModel;
import com.example.model.StudentModel;
import com.example.model.UniversitasModel;

public interface StudentService
{
	StudentModel selectStudent (String npm);
    ProdiModel selectProdi(int id);
    FakultasModel selectFakultas(int id);
    UniversitasModel selectUniversitas(int id);

    String selectStudentNPM (String npm);
    List<StudentModel> selectAllStudents ();
    List<ProdiModel> selectAllProdi ();
    List<FakultasModel> selectAllFakultas ();
    List<UniversitasModel> selectAllUniversitas ();
    int selectUniv (int id);
    int selectFak (int id);
    List<FakultasModel> selectAllFakultasbyUniv (int id_univ);
    List<ProdiModel> selectProdibyFak (int id_fakultas);
    List<StudentModel> selectMahasiswaByProdi(int id_prodi);
    
    void addMahasiswa (StudentModel mahasiswa);

    void deleteMahasiswa (String npm);

    void updateMahasiswa (StudentModel mahasiswa);
    
    
	int cekKelulusan(String tahun_masuk, int id_prodi);
	
	int cekKelulusanStatus(String tahun_masuk, int id_prodi);
	
	int cariMahasiswa(int id_univ);
}
