package com.insider.login.department.service;

import com.insider.login.department.dto.DepartmentDTO;
import com.insider.login.department.entity.Department;
import com.insider.login.department.repository.DepartmentRepository;
import com.insider.login.member.dto.MemberDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class DepartmentService {

    private final ModelMapper modelMapper;
    private final DepartmentRepository departmentRepository;

    public DepartmentService (ModelMapper modelMapper, DepartmentRepository departmentRepository) {
        this.modelMapper = modelMapper;
        this.departmentRepository = departmentRepository;
    }




//    public String updateDepartment(int departNo) {
//        Department department =departmentRepository.findById(departNo).orElse(null);



    @Transactional
    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO) {
        Department department = modelMapper.map(departmentDTO, Department.class);
        Department updatedDepartment = departmentRepository.save(department);
        DepartmentDTO updatedDepartmentInfo = modelMapper.map(updatedDepartment, DepartmentDTO.class);
        return updatedDepartmentInfo;
    }

//    @Transactional
//    public DepartmentDTO updateDepartment123(DepartmentDTO departmentDTO) {
//
//    }


    @Transactional
    public void insertDepartment(DepartmentDTO departmentDTO) {
        Department department = modelMapper.map(departmentDTO, Department.class);
        departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(int departNo) {
        departmentRepository.deleteById(departNo);
    }

    public List<DepartmentDTO> searchAllDepartment() {
        System.out.println("all departments: " + departmentRepository.findAll());
        List<Department> department = departmentRepository.findAll();
        Type listType = new TypeToken<List<DepartmentDTO>>() {}.getType();
        List<DepartmentDTO> departmentList = modelMapper.map(department, listType);
        return departmentList;
    }

    public String findDepartName(int departNo) {
        Department existingDepartName = departmentRepository.findById(departNo).orElse(null);
        DepartmentDTO foundDepartName = modelMapper.map(existingDepartName, DepartmentDTO.class);

        return foundDepartName.getDepartName();
    }

//    public DepartmentDTO findDepartment(int departNo) {
//        Department department = departmentRepository.findById(departNo).orElse(null);
//        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
//        return departmentDTO;
//    }
}

