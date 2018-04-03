package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.FakultasModel;
import com.example.model.ProdiModel;
import com.example.model.StudentModel;
import com.example.model.UniversitasModel;

@Mapper
public interface StudentMapper
{
    @Select("select * from mahasiswa where npm=#{npm}")
    StudentModel selectStudent (@Param("npm") String npm);
    
    @Select("select * from Mahasiswa")
    List<StudentModel> selectAllStudents ();
    
    
    @Select("SELECT * FROM program_studi m")
    List<ProdiModel> selectAllProdi();
	
	@Select("SELECT * FROM program_studi WHERE id = #{id}")
	ProdiModel selectProdi (@Param("id") int id);
   
	@Select("SELECT * FROM fakultas")
    List<FakultasModel> selectAllFakultas();
    
    @Select("SELECT * from fakultas where id = #{id}")
    FakultasModel selectFakultas (@Param("id")int id);
    
    @Select("SELECT * FROM universitas")
    List<UniversitasModel> selectAllUniversitas();
    
    @Select("SELECT * FROM universitas WHERE id = #{id}")
    UniversitasModel selectUniversitas (@Param("id") int id);

    @Select("SELECT substring(npm, 10,3) FROM mahasiswa WHERE npm LIKE '${npm}%' ORDER BY substring(npm, 10, 3) DESC LIMIT 1")
    String selectStudentNPM (@Param("npm")String npm);
    
	void deleteMahasiswa(String npm);
    
    @Insert("INSERT into mahasiswa (npm, nama,tempat_lahir,tanggal_lahir,jenis_kelamin,agama,golongan_darah,tahun_masuk,jalur_masuk,status,id_prodi) "
    		+ "VALUES (#{npm},#{nama}, #{tempat_lahir}, #{tanggal_lahir},#{jenis_kelamin},#{agama},"
    		+ "#{golongan_darah},#{tahun_masuk},#{jalur_masuk},#{status},#{id_prodi})")
    void addMahasiswa (StudentModel mahasiswa);
    
    @Update("UPDATE mahasiswa SET npm = #{npm}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir=#{tanggal_lahir}, jenis_kelamin=#{jenis_kelamin},"
    		+ "agama=#{agama},golongan_darah=#{golongan_darah}, tahun_masuk=#{tahun_masuk}, jalur_masuk=#{jalur_masuk},"
    		+ "id_prodi=#{id_prodi} WHERE id = #{id}")
    void updateStudent (StudentModel student);
    
    @Select("select count(*) from mahasiswa where tahun_masuk =#{tahun_masuk} and id_prodi= #{prodi} and status = 'Lulus'")
    Integer cekKelulusan (@Param ("tahun_masuk") String tahun_masuk, @Param("prodi")int prodi);
    
    @Select("select count(*) from mahasiswa where tahun_masuk=#{tahun_masuk} and id_prodi= #{prodi}")
    Integer cekKelulusanStatus (@Param ("tahun_masuk") String tahun_masuk, @Param("prodi")int prodi);
    
    @Select ("Select * from fakultas where id_univ=#{id}")
    int cariUniversitas (@Param("id_univ")int id_univ);
    
    @Select("SELECT * FROM universitas WHERE id = #{id}")
    int selectUniv (@Param("id") int id);
    
    @Select("SELECT * FROM fakultas WHERE id = #{id}")
    int selectFak(@Param("id") int id);
    
    @Select("SELECT * FROM fakultas where id_univ=#{id_univ}")
    List<FakultasModel> selectAllFakultasbyUniv(@Param ("id_univ")int id_univ);
    
    @Select("SELECT * FROM program_studi where id_fakultas=#{id_fakultas}")
    List<ProdiModel> selectProdibyFak(@Param ("id_fakultas")int id_fakultas);
    
    @Select("SELECT * FROM mahasiswa where id_prodi = #{id_prodi}")
    List<StudentModel> selectMahasiswaByProdi (@Param ("id_prodi") int id_prodi);
}
