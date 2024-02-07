package application;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entity.Department;
import model.entity.Seller;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();     

        System.out.println("\n-Test findById!\n");

        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
        System.out.println();

        System.out.println("\n-Test findAll!\n");

        List<Seller> list = new ArrayList<>();
        list = sellerDao.findAll();
        list.forEach(System.out::println);

        System.out.println("\n-Test findByDepartment!\n");

        Department dep = new Department(4,"");
        list = sellerDao.findByDepartment(dep);
        list.forEach(System.out::println);

        System.out.println("\n-Test Insert\n");

        Seller obj = new Seller(null, "Gustavo", "gustavo@example.com", new Date(), 4.500, dep);
        sellerDao.insert(obj);
        System.out.println("Done! Inserted new seller: "+ obj.getName());

        System.out.println("\n-Test seller update!\n");

        seller = sellerDao.findById(12);
        seller.setBaseSalary(4500.00);
        sellerDao.update(seller);
        System.out.println("Update completed!");

        System.out.println("\n-Test delete!\n");
        
        sellerDao.deleteById(9);
        System.out.println("Delete completed!");

    }
}
