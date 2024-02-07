package application;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.SourceDataLine;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entity.Department;

public class Main2 {
    public static void main(String[] args) {
        //Department

        DepartmentDao depDao = DaoFactory.createDepartmentDao();

        System.out.println("\n-Test findById!\n");

        Department dep = depDao.findById(3);
        System.out.println(dep);
        SellerDao sellerDao = DaoFactory.createSellerDao();
        
        System.out.println("\n-Test findAll!\n");

        List<Department> list = new ArrayList<>();
        list= depDao.findAll();
        list.forEach(System.out::println);

        System.out.println("\n-Test insert!\n");

        Department newDep = new Department(null, "Cars");
        //depDao.insert(newDep);
        list.forEach(System.out::println);
        System.out.println("Inserte completed!");

        System.out.println("\n-Test deleteById!\n");

        depDao.deleteById(9);
        depDao.deleteById(10);
        depDao.deleteById(11);
        depDao.deleteById(12);
        depDao.deleteById(13);

        System.out.println("Delete completed!");

        System.out.println("\n-Test update\n");

        Department dep2 = depDao.findById(6);
        dep2.setName("Motorcycle");
        depDao.update(dep2);
        list.forEach(System.out::println);

    }
}
