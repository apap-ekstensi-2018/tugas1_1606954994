package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.StudentMapper;
import com.example.model.FakultasModel;
import com.example.model.ProdiModel;
import com.example.model.StudentModel;
import com.example.model.UniversitasModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceDatabase implements StudentService
{
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public StudentModel selectStudent (String npm)
    {
        log.info ("select mahasiswa with npm {}", npm);
        return studentMapper.selectStudent (npm);
    }
    @Override
    public List<StudentModel> selectAllStudents ()
    {
        log.info ("select all students");
        return studentMapper.selectAllStudents ();
    }
	@Override
	public ProdiModel selectProdi(int id) {
		return studentMapper.selectProdi(id);
	}
	
	@Override
	public List<ProdiModel> selectAllProdi() {
		 return studentMapper.selectAllProdi ();
	}
	@Override
	public FakultasModel selectFakultas(int id) {
		return studentMapper.selectFakultas(id);
	}
	
	@Override
	public List<FakultasModel> selectAllFakultas() {
		 return studentMapper.selectAllFakultas ();
	}
	@Override
	public UniversitasModel selectUniversitas(int id) {
		return studentMapper.selectUniversitas(id);
	}
	
	@Override
	public List<UniversitasModel> selectAllUniversitas() {
		 return studentMapper.selectAllUniversitas ();
	}
	@Override
	public void addMahasiswa(StudentModel mahasiswa) {
		studentMapper.addMahasiswa(mahasiswa);
	}
	@Override
	public void deleteMahasiswa(String npm) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateMahasiswa(StudentModel mahasiswa) {
		studentMapper.updateStudent(mahasiswa);
	}
	@Override
	public String selectStudentNPM(String npm) {
		return studentMapper.selectStudentNPM (npm);
	}
	@Override
	public int cekKelulusan(String tahun_masuk, int id_prodi) {
		log.info ("select persentase kelulusan");
        return studentMapper.cekKelulusan(tahun_masuk,id_prodi);
		
	}
	@Override
	public int cekKelulusanStatus(String tahun_masuk, int id_prodi) {
		log.info ("select persentase kelulusan with status");
        return studentMapper.cekKelulusanStatus(tahun_masuk,id_prodi);
	}
	@Override
	public int cariMahasiswa(int id_univ) {
		log.info ("select persentase kelulusan with status");
        return studentMapper.cariUniversitas(id_univ);
	}
	@Override
	public int selectUniv(int id) {
		log.info ("select Univ");
		return studentMapper.selectUniv(id);
	}
	@Override
	public int selectFak(int id) {
		log.info ("select Fak");
		return studentMapper.selectFak(id);
	}
	@Override
	public List<FakultasModel> selectAllFakultasbyUniv(int id_univ) {
		log.info ("select All Fak");
		return studentMapper.selectAllFakultasbyUniv(id_univ);
	}
	@Override
	public List<ProdiModel> selectProdibyFak(int id_fakultas) {
		log.info ("select All Prodi");
		return studentMapper.selectProdibyFak(id_fakultas);
	}
	@Override
	public List<StudentModel> selectMahasiswaByProdi(int id_prodi) {
		log.info(" select mahasiswa by prodi ");
		return studentMapper.selectMahasiswaByProdi(id_prodi);
	}
	
	
}
