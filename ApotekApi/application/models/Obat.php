<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Obat extends CI_Model {

	public function getAllobat()
	{
		return $this->db->get('daftar_obat')->result_array();
	}

	public function postObat($data)
	{
		$this->db->insert('daftar_obat', $data);
		return $this->db->affected_rows();
	}
}

/* End of file Obat.php */
/* Location: ./application/models/Obat.php */