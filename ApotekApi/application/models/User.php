<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class User extends CI_Model {


public function regUser($data)
{
	$this->db->insert('user', $data);
	return $this->db->affected_rows();
}

public function showUser($id=null)
{
	if ($id == null) {
		
		return $this->db->get('user')->result_array();
	}
	else
	{
		return $this->db->get_where('user',['id'=>$id])->result_array();
	}
}
public function updateUser($data,$id)
{
	$this->db->update('user', $data,['id'=>$id]);
	return $this->db->affected_rows();
}

}

/* End of file User.php */
/* Location: ./application/models/User.php */