using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace Resource_calculator_service
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            

        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {
           
        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            string rideid = textBox1.Text;
        }

        private void button1_Click(object sender, EventArgs e)
        {

            string rideid = textBox1.Text;
            String str;
            int km=0, co2=0, price=0;
            SqlConnection myConn = new SqlConnection("Server=(LocalDB)\\MSSQLLocalDB;Integrated security=SSPI;database=master");
            str = "SELECT km FROM rides2 WHERE rideid='" + rideid.ToString() + "';";
            SqlCommand command = new SqlCommand(str, myConn);
            try
            {
                myConn.Open();
                SqlDataReader reader = command.ExecuteReader();
         
                while (reader.Read())
                {
                   // MessageBox.Show(reader["km"].ToString(), "MyProgram", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    km = Convert.ToInt32(reader["km"]);
                    co2 = 130 * km / 1000;
                    price = 10 * km;
                   // String query = "UPDATE rides2 SET co2 = '" + co2.ToString() + "', price= '" + price.ToString() + "' WHERE rideid = '" + rideid.ToString() + ";";

                    //command = new SqlCommand(query, myConn);
                    

                    //command.ExecuteNonQuery();

                }

            }
            catch (System.Exception ex)
            {
                MessageBox.Show(ex.ToString(), "MyProgram", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            finally
            {
                if (myConn.State == ConnectionState.Open)
                {
                    myConn.Close();
                }
            }
            myConn.Open();
            String query = "UPDATE rides2 SET co2 = '" + co2.ToString() + "', price= '" + price.ToString() + "' WHERE rideid = '" + rideid.ToString() + "';";
            command = new SqlCommand(query, myConn);
            command.ExecuteNonQuery();
            myConn.Close();

            //int km = 70;
            label8.Text = km.ToString() + "km";
            label4.Text = co2.ToString() + "kg.";
            label7.Text = price.ToString() + "rub.";
        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void label5_Click(object sender, EventArgs e)
        {

        }

        private void label6_Click(object sender, EventArgs e)
        {

        }

        private void label7_Click(object sender, EventArgs e)
        {

        }

        private void label8_Click(object sender, EventArgs e)
        {

        }
    }
}
