<?php
defined('BASEPATH') OR exit('No direct script access allowed');

use chriskacerguis\RestServer\RestController;
class Api extends RestController {

	function __construct()
    {
        // Construct the parent class
        parent::__construct();
        $this->load->model('User');
        $this->load->model('Pesan');
        $this->load->model('Obat');
    }

	// Bag daftar
	public function daftar_post()
	{
		$pass = $this->post('password',true);

		$password = password_hash($pass,PASSWORD_BCRYPT);
		$data = [
			"nama"=>$this->post('nama',true),
			"alamat"=>$this->post('alamat',true),
			"telp"=>$this->post('telp',true),
			"username"=>$this->post('username',true),
			"password"=> $password
		];

		if ($this->User->regUser($data)>0) {
			$this->response([
				'error'=>false,
				'message' => 'Congratulation you have been added'
			],200);
		}
		else
		{
			$this->response([
				'error'=>true,
				'message'=> 'Terjadi kesalahan'
			],200);
		}
	}
	// Akhir daftar

	// Bag login
	public function login_post()
	{
		$name = $this->post('username',true);
        $pass = $this->post('password',true);

        $user = $this->db->get_where('user',['username' =>$name])->row_array();

        if ($user) 
        {
            if (password_verify($pass,$user['password'])) 
            {
                $this->response([
                'error' => false,
                'message' => 'Login successfull',
                'user' => $user
                ],200);
            }
            else
            {
                $this->response([
                'error' => true,
                'message' => 'Password salah'
                ],200);
            }
        }
        else
        {
            $this->response([

                'error'=>true,
                'message'=>"Username dan Password salah"
            ],200);
        }
	}
	// Akhir Login

	// Bag showStatus
	public function status_get()
	{
		$data = $this->Pesan->getStatus();

		if ($data) {
			$this->response($data,200);
		}
		else
		{
			$this->response(
				['error'=>true,
				'message'=>'Data tidak ada'
			],200);
		}
	}
	// Akhir showstatus

	// Bag ShowPesan
	public function pesan_get()
	{
		$id = $this->get('id_user');

		if ($id == null) {
			$data = $this->Pesan->showPesan();
		}
		else
		{
			$data = $this->Pesan->showPesan($id);
		}

		if ($data) {
			$this->response($data,200);
		}
		else
		{
			$this->response([
				'error'=>true,
				'message'=>'Id user tidak ada'
			],200);
		}
	}
	// Akhir bag showpesan

	// bag pesan
	public function pesan_post()
	{
		date_default_timezone_set('Asia/Jakarta');
		$date = date('Y-m-d');
		$pesan = [
			'nama_obat'=>$this->post('nama_obat',true),
			'jumlah'=>$this->post('jumlah',true),
			'harga'=>$this->post('harga',true),
			'tanggal'=>$date,
			'id_user'=>$this->post('id_user',true),
			'id_stat'=> 1
		];
		$jml = $this->post('jumlah',true);
		$nama = $this->post('nama_obat');

		if ($this->Pesan->Order($pesan)>0) {
			$this->Pesan->updateJumlah($nama,$jml);
			$this->response([
				'error'=>false,
				'message'=>'Order berhasil'
			],200);
		}
		else
		{
			$this->response([
				'error'=>true,
				'message'=>'Terjadi kesalahan'
			],200);
		}
		
	}
	// akhir pesan

	// Bag showUser
	public function user_get()
	{
		$id = $this->get('id');
		if ($id == null) {
			$data = $this->User->showUser();
		}
		else
		{
			$data = $this->User->showUser($id);
		}

		if ($data) {
			$this->response($data,200);
		}
		else
		{
			$this->response([
				'error'=>true,
				'message'=>'Id tidak ada'
			],200);
		}

	}
	// Akhir showUser

	// Bag update User
	public function updateUser_put()
	{
		$data = [
			"id"=>$this->put('id'),
			"nama"=>$this->put('nama',true),
			"alamat"=>$this->put('alamat',true),
			"telp"=>$this->put('telp',true),
			"username"=>$this->put('username',true)
		];
		$id = $this->put('id');

		if ($this->User->updateUser($data,$id)>0) {
			$this->response([
				'error'=>false,
				'message'=>'User berhasil diupdate',
				'user'=>$data
			],200);
		}
		else
		{
			$this->response([
				'error'=>true,
				'message'=>'User gagal diupdate'
			],200);
		}
	}
	// Akhir update User

	public function checkout_post()
	{
		
		$id = $this->post('id');
		// $checkout = md5(uniqid($id));
		$name = base64_decode($this->post('checkout'));
		$config = array(
			'upload_path' => "asset/img/checkout",
			'allowed_types' => "jpg|png|jpeg",
			'max_size' => '100',
			'file_name' => $name
		);
		$this->load->library('upload', $config);

		if ($this->upload->do_upload('checkout')) {
			$data = array('upload_data' => $this->upload->data());
			$path = "http://192.168.43.83/Apotek/" . $config['upload_path'] . '/' . $data['upload_data']['file_name'];
			$this->db->where('id', $id);
			$this->db->update('pesan', ['checkout' => $path]);
			// $this->Pesan->updatePesan($id,$path);
			$returndata = array('error' => false, 'message' => ' upload successfully');
			$this->set_response($returndata, 200);
		} else {
			$error = array('error' => $this->upload->display_errors());
			$returndata = array('error' => true, 'message' => $error);
			$this->set_response($returndata, 200);

			}
	}
	// Akhir update image

	//Bag getAll Obat
	public function obat_get()
	{
		$data = $this->Obat->getAllObat();
		if ($data) {
			$this->response($data,200);
		}
		else
		{
			$this->response([
				'error'=>false,
				'message'=>'Obat kosong'
			],200);
		}
	}
	 //Akhir getAllObat 

	// Bag postObat
	public function obat_post()
	{
		
		$name = base64_decode($this->post('image'));
		$config = array(
			'upload_path' => "asset/img/obat",
			'allowed_types' => "jpg|png|jpeg",
			'max_size' => '100',
			'file_name' => $name
		);
		
		$this->load->library('upload', $config);

		if ($this->upload->do_upload('image')) {
			$data = array('upload_data' => $this->upload->data());
			$path ="http://192.168.43.83/Apotek/" . $config['upload_path'] . '/' . $data['upload_data']['file_name'];
			$obat = [
				'nama_obat'=>$this->post('nama_obat'),
				'harga'=>$this->post('harga'),
				'jumlah'=>$this->post('jumlah'),
				'image'=>$path
			];
			if ($this->Obat->postObat($obat)>0) {
				$returndata = array('error' => false, 'message' => 'Insert berhasil');
				$this->set_response($returndata, 200);
			}
			else
			{
				$returndata = array('error' => false, 'message' => 'Insert gagal');
				$this->set_response($returndata, 200);
			}
			
		} else {
			$error = array('error' => $this->upload->display_errors());
			$returndata = array('error' => true, 'message' => $error);
			$this->set_response($returndata, 200);

			}
	}
	// Akhir postObat
	// Bag updatefotouser
	public function updateFotoUser_post()
	{
		$id = $this->post('id');
		// $checkout = md5(uniqid($id));
		$name = base64_decode($this->post('foto'));
		$config = array(
			'upload_path' => "asset/img/fotouser",
			'allowed_types' => "jpg|png|jpeg|gif",
			'max_size' => '100',
			'file_name' => $name
		);
		$this->load->library('upload', $config);

		if ($this->upload->do_upload('foto')) {
			$data = array('upload_data' => $this->upload->data());
			$path = "http://192.168.43.83/Apotek/" . $config['upload_path'] . '/' . $data['upload_data']['file_name'];
			$this->db->where('id', $id);
			$this->db->update('user', ['foto' => $path]);
			// $this->Pesan->updatePesan($id,$path);
			$returndata = array('error' => false, 'message' => ' upload successfully');
			$this->set_response($returndata, 200);
		} else {
			$error = array('error' => $this->upload->display_errors());
			$returndata = array('error' => true, 'message' => $error);
			$this->set_response($returndata, 200);

			}
	}
	// akhir update foto user
}

/* End of file newApi.php */
/* Location: ./application/controllers/newApi.php */