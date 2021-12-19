import {Box, Container} from "@mui/material";
import Avatar from "@mui/material/Avatar";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import {useNavigate} from "react-router-dom";

interface Props {
  title: string;
  content: string;
}

const TokenResult = ({title, content}: Props) => {

  const navigate = useNavigate();

  return (
    <Container component="main" sx={{
      border: '1px solid #000000',
      boxShadow: '6px 6px 0px #00bfff',
      backgroundColor: 'white',
      mt: 20,
      paddingTop: 8,
      paddingBottom: 2,
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center'
    }} maxWidth="xs">
      <Avatar sx={{m: 1, bgcolor: 'primary.main'}}>
        <LockOutlinedIcon/>
      </Avatar>
      <Typography component="h1" variant="h4">
        {title}
      </Typography>
      <Box sx={{mt: 1}}>
        <p>{content}</p>
        <Button
          fullWidth
          variant="contained"
          sx={{mt: 3, mb: 2}}
          onClick={() => navigate('/login')}
        >
          Return to sign in
        </Button>
      </Box>
    </Container>
  );
}

export default TokenResult;