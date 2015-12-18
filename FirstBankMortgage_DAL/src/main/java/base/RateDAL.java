package base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.RateDomainModel;
import util.HibernateUtil;

public class RateDAL {


	public static double getRate(int GivenCreditScore) {
		//FinalExam - please implement		
		// Figure out which row makes sense- return back the 
		// right interest rate from the table based on the given credit score
		//FinalExam - obviously change the return value
		/**
		 * IDK IF I WAS ALLOWED TO USE STUDENTDAL AS AN EXAMPLE FOR THIS DAL?
		 */
		//Tests amount of rows and checks for >0
			Session session = HibernateUtil.getSessionFactory().openSession();
			
			Transaction tx = null;
			
			RateDomainModel Get_Rate = null;

			try {
				
				tx = session.beginTransaction();

				Query query = session.createQuery("from RateDomainModel where MinCreditScore = :MINCREDITSCORE ");
				//Sets MINCREDITSCORE with givben credit score
				query.setParameter("MINCREDITSCORE", GivenCreditScore);

				Get_Rate = (RateDomainModel) query.list().get(0);

				tx.commit();

			} catch (IndexOutOfBoundsException ex) {
				
				Get_Rate = null;
			} 
			catch (HibernateException e) {
				
				if (tx != null)
					
					tx.rollback();
				
				e.printStackTrace();
			} 
			finally {
				
				session.close();
			}
			
			return Get_Rate.getInterestRate();
			
		}
		
	//tests to see if there are more than 0 rows
		public static ArrayList<RateDomainModel> selectRate(){
			
			Session session = HibernateUtil.getSessionFactory().openSession();
			
			Transaction tx = null;
			
			RateDomainModel Get_Rate = null;		
			
			ArrayList<RateDomainModel> rates = new ArrayList<RateDomainModel>();
			
			try {
				tx = session.beginTransaction();	
				
				List List_rates = session.createQuery("FROM RateDomainModel").list();
				
				for (Iterator iterator = List_rates.iterator(); iterator.hasNext();) {
					
					RateDomainModel newRate = (RateDomainModel) iterator.next();
					
					rates.add(newRate);

				}
				
				
				tx.commit();
			} 
			catch (HibernateException e) {
				
				if (tx != null)
					
					tx.rollback();
				
				e.printStackTrace();
			
			} finally {
				session.close();
			}		
			
			return rates;

		}
	}


