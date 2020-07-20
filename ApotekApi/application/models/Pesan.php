<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Pesan extends CI_Model {

	public function Order($data)
	{

		$this->db->insert('pesan', $data);
		return $this->db->affected_rows();
	}

	public function showPesan($id = null)
	{
		if ($id == null) {
			return $this->db->get('pesan')->result_array();
		}
		else
		{
			return $this->db->get_where('pesan',[ 'id_user' => $id ])->result_array();
		}
	}
	public function getStatus()
	{
		return $this->db->get('status')->result_array();
	}

	public function updatePesan($id,$path)
	{
		$this->db->set('checkout');
		$this->db->where('id', $id);
		$this->db->replace('pesan', $path);
		return $this->db->affected_rows();
	}

	public function updateJumlah($nama,$jml)
	{
		$data = $this->db->select('jumlah')->get_where('daftar_obat',['nama_obat'=>$nama])->row_array();
		$stok = $data['jumlah'];
		$tot = $stok - $jml;
		$this->db->set('jumlah',$tot);
		$this->db->where('nama_obat',$nama);
		$this->db->update('daftar_obat');
	}

}

/* End of file Pesan.php */
/* Location: ./application/models/Pesan.php */