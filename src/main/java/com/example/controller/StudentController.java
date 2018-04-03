package com.example.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.FakultasModel;
import com.example.model.StudentModel;
import com.example.model.ProdiModel;

import com.example.model.UniversitasModel;
import com.example.service.StudentService;



@Controller
public class StudentController {
	@Autowired
	StudentService studentDAO;
	
	
	@RequestMapping("/")
	public String index ()
    {
        return "index";
    }
	
	 @RequestMapping("/mahasiswa{npm}")
	    public String view (Model model,
	            @RequestParam(value = "npm", required = false) String npm)
	    {
	        StudentModel mahasiswa = studentDAO.selectStudent(npm);
	        ProdiModel prodi = studentDAO.selectProdi(mahasiswa.getId_prodi());
	        FakultasModel fakultas = studentDAO.selectFakultas(prodi.getId_fakultas());
	        UniversitasModel universitas = studentDAO.selectUniversitas(fakultas.getId_univ());
	        

	        if (mahasiswa != null) {
	            model.addAttribute ("mahasiswa", mahasiswa);
	            model.addAttribute ("prodi", prodi);
	            model.addAttribute ("fakultas", fakultas);
	            model.addAttribute ("universitas", universitas);
	            return "form-lihat";
	        } else {
	        		model.addAttribute ("npm", npm);
	            return "not-found";
	        }
	    }
	
	@RequestMapping("/mahasiswa/tambah")
	public String tambah (@ModelAttribute("mahasiswa") StudentModel mahasiswa, Model model)
	{
		if(mahasiswa.getNama()==null) {
			return "form-tambah";
		} else {
			mahasiswa.setNpm(generateNpm(mahasiswa));
			mahasiswa.setStatus("Aktif");
			model.addAttribute("npm", mahasiswa.getNpm());
			model.addAttribute("message", "Mahasiswa dengan NPM " + mahasiswa.getNpm() + " berhasil ditambahkan");
			studentDAO.addMahasiswa(mahasiswa);
			return "berhasil-tambah";
		}
	}
	
	public String generateNpm(StudentModel mahasiswa) {
		ProdiModel prodi = studentDAO.selectProdi(mahasiswa.getId_prodi());
		FakultasModel fakultas = studentDAO.selectFakultas(prodi.getId_fakultas());
		UniversitasModel universitas = studentDAO.selectUniversitas(fakultas.getId_univ());
		
		String duadigit  = mahasiswa.getTahun_masuk().substring(2, 4);
		String upDigit = universitas.getKode_univ()+prodi.getKode_prodi();
		String tigadigit="";
		    	if (mahasiswa.getJalur_masuk().equals("Olimpiade Undangan")) {
		    		tigadigit = "53";
		    	}
		    	else if(mahasiswa.getJalur_masuk().equals("Undangan Reguler/SNMPTN")) {
		    		tigadigit="54";
		    	}
		    	else if (mahasiswa.getJalur_masuk().equals("Undangan Paralel/PPKB")) {
		    		tigadigit="55";
		    	}
		    	else if (mahasiswa.getJalur_masuk().equals("Ujian Tulis Mandiri")) {
		    		tigadigit="62";
		    	}
		    	String npmFiks = duadigit + upDigit + tigadigit;
		    	String cekNpm = studentDAO.selectStudentNPM(npmFiks);
		    	if(cekNpm != null) {
		    	cekNpm = "" + (Integer.parseInt(cekNpm) + 1);
		    	if (cekNpm.length() == 1) {
    			npmFiks = npmFiks + "00" + cekNpm;
    		}else if (cekNpm.length() == 2) {
    			npmFiks = npmFiks + "0" + cekNpm;
    		}else {
    			npmFiks = npmFiks + cekNpm;
    		}
    	}else {
    		npmFiks = npmFiks + "001";
    	}
		    	return npmFiks;
	}
	
	@RequestMapping("/mahasiswa/update/{npm}")
    public String ubah (@PathVariable(value = "npm") String npm, Model model, 
    		@ModelAttribute("mahasiswa") StudentModel mahasiswabaru)
    {
    		StudentModel mahasiswa = studentDAO.selectStudent(npm);
    		
    		if(mahasiswabaru.getNama()==null) {
    			if(mahasiswa==null) {
    				model.addAttribute("npm", npm);
    				return "not-found";
    			}
    			model.addAttribute("mahasiswa", mahasiswa);
    			model.addAttribute("title", "Update Mahasiswa");
    			return "form-ubah";
    		} else {
    			if(!mahasiswa.getTahun_masuk().equals(mahasiswabaru.getTahun_masuk()) || 
    					mahasiswa.getId_prodi() != mahasiswabaru.getId_prodi() || 
    					!mahasiswa.getJalur_masuk().equals(mahasiswabaru.getJalur_masuk())) {
    				mahasiswabaru.setNpm(generateNpm(mahasiswabaru));

        			studentDAO.updateMahasiswa(mahasiswabaru);
        			model.addAttribute("npm", mahasiswa.getNpm());
        			model.addAttribute("title", "Update Mahasiswa");
        			return "berhasil-ubah";
    			}else {
    				mahasiswabaru.setNpm(mahasiswa.getNpm());

        			studentDAO.updateMahasiswa(mahasiswabaru);
        			model.addAttribute("npm", mahasiswa.getNpm());
        			model.addAttribute("title", "Update Mahasiswa");
        			return "berhasil-ubah";
    			}
    		}
    }
    
	 @RequestMapping("/kelulusan")
	    public String kelulusan(Model model,
	    						@RequestParam(value = "tahun_masuk", required = false) Optional<String> tahun_masuk,
	    						@RequestParam(value = "prodi", required = false) Optional<String> prodi)
	{
		if (tahun_masuk.isPresent() && prodi.isPresent()) {
			int jml_mhs = studentDAO.cekKelulusanStatus(tahun_masuk.get(), Integer.parseInt(prodi.get()));
			int jml_mhsLulus = studentDAO.cekKelulusan(tahun_masuk.get(), Integer.parseInt(prodi.get()));
			double persen = ((double) jml_mhsLulus / (double) jml_mhs) * 100;
			String persentase = new DecimalFormat("##.##").format(persen);
			ProdiModel program_studi = studentDAO.selectProdi(Integer.parseInt(prodi.get()));
			FakultasModel fakultas = studentDAO.selectFakultas(program_studi.getId_fakultas());
			UniversitasModel universitas = studentDAO.selectUniversitas(fakultas.getId_univ());
			model.addAttribute("jml_mhs", jml_mhs);
			model.addAttribute("jml_mhsLulus", jml_mhsLulus);
			model.addAttribute("persentase", persentase);
			model.addAttribute("tahun_masuk", tahun_masuk.get());
			model.addAttribute("prodi", program_studi.getNama_prodi());
			model.addAttribute("fakultas", fakultas.getNama_fakultas());
			model.addAttribute("universitas", universitas.getNama_univ());
			
			return "lihat-lulus";
			
		}else {
			List<ProdiModel> programStudi = studentDAO.selectAllProdi();
			model.addAttribute("programStudi", programStudi);
			return "form-lulus";
		}
	} 
	 
	@RequestMapping("/mahasiswa/cari")
	 public String cari (Model model,
			 @RequestParam(value = "universitas", required = false) String univ,
				@RequestParam(value = "idfakultas", required = false) String idfakultas,
				@RequestParam(value = "idprodi", required = false) String idprodi)
		{

				List<UniversitasModel> universitas = studentDAO.selectAllUniversitas();
				model.addAttribute ("universitas", universitas);
	            	if(univ== null) {
	            		return "lihat-cari";
	            	} else {
	            		int idUniv = Integer.parseInt(univ);
	            		UniversitasModel univers = studentDAO.selectUniversitas(idUniv);
	            		int idUnivv = studentDAO.selectUniv(idUniv);
	            		List<FakultasModel> fakultas = studentDAO.selectAllFakultasbyUniv(idUnivv);
	            		if (idfakultas == null) {
	            			model.addAttribute("fakultas", fakultas);
	            			model.addAttribute("selectUniv",idUniv);
	                		return "form-cariFakultas";
	            		}
	            		else {
	            			int idFakul = Integer.parseInt(idfakultas);
	            			FakultasModel fakultass = studentDAO.selectFakultas(idFakul);
	            			int idFaks = studentDAO.selectFak(idFakul);
	            			model.addAttribute("selectFak", idFakul);
	            			List<ProdiModel> prodd = studentDAO.selectProdibyFak(idFaks);
	            			
	            			if(idprodi == null) {
	            				model.addAttribute("fakultas", fakultas);
	                			model.addAttribute("selectUniv",idUniv);
	                			model.addAttribute("prodi",prodd);
	                			
	                    		return "form-cariProdi";
	            			}else {
	            				int idprod = Integer.parseInt(idprodi);
	            				ProdiModel prodis = studentDAO.selectProdi(idprod);
	            				List<StudentModel> mahasiswaByProdi = studentDAO.selectMahasiswaByProdi(idprod);
	            				model.addAttribute("mahasiswaByProdi", mahasiswaByProdi);
	            				ProdiModel prodiSelectObject = studentDAO.selectProdi(idprod);
	            				model.addAttribute("prodiSelectObject", prodiSelectObject);
	            				
	            				return "form-tabelMhs";
	            			}
	        
	  }
	}	
	}
}	
