import * as yup from "yup";
import {FC} from "react";
import {Controller, SubmitHandler, useForm} from "react-hook-form";
import {yupResolver} from "@hookform/resolvers/yup";
import {useNavigate, useSearchParams} from "react-router-dom";
import {toast} from "react-toastify";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Avatar from "@mui/material/Avatar";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import Link from "@mui/material/Link";
import {changePassword} from "../../api/reset-password";
import {Copyright} from "../../components/copyright";

interface IFormInputs {
  password: string;
  confirmPassword: string;
}

const schema = yup.object().shape({
  password: yup.string().min(4).max(20).required(),
  confirmPassword: yup.string().oneOf([yup.ref('password'), null], 'Passwords must match'),
});

const ChangePasswordForm: FC = () => {

  const [searchParams] = useSearchParams();

  const token: string = searchParams.get("token") ?? '';

  const {
    handleSubmit,
    control,
    formState: {errors},
  } = useForm<IFormInputs>({resolver: yupResolver(schema)})

  const navigate = useNavigate();

  const formSubmitHandler: SubmitHandler<IFormInputs> = (data: IFormInputs) => {

    changePassword({newPassword: data.password, token: token})
      .then(response => {
        if (response.status === 200) {
          if (response.data === 'success') {
            toast.success('Changed password successfully.', {
              position: "top-right",
              autoClose: 5000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });
            navigate('/login');
          } else {
            toast.error('Failed to change password.', {
              position: "top-right",
              autoClose: 5000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });
            navigate(`/change-password/${response.data}`);
          }
        }
      })
      .catch(err => {
        toast.error('Failed to change password.', {
          position: "top-right",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        navigate('/login');
      });
  }

  return (
    <Container component="main" sx={{
      border: '1px solid #000000',
      boxShadow: '6px 6px 0px #00bfff',
      backgroundColor: 'white',
      mt: 20,
      paddingTop: 8,
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center'
    }} maxWidth="xs">
      <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
        <LockOutlinedIcon/>
      </Avatar>
      <Typography component="h5" variant="h4">
        Set new password
      </Typography>
      <Box component="form" onSubmit={handleSubmit(formSubmitHandler)} noValidate
           sx={{mt: 1}}>
        <Controller
          name='password'
          control={control}
          defaultValue=''
          render={({field}) => (
            <TextField
              {...field}
              variant="outlined"
              error={!!errors.password}
              helperText={errors.password ? errors.password?.message : ''}
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
            />
          )}
        />

        <Controller
          name='confirmPassword'
          control={control}
          defaultValue=''
          render={({field}) => (
            <TextField
              {...field}
              variant="outlined"
              error={!!errors.confirmPassword}
              helperText={errors.confirmPassword ? errors.confirmPassword?.message : ''}
              margin="normal"
              required
              fullWidth
              name="confirmPassword"
              label="Confirm Password"
              type="confirmPassword"
              id="confirmPassword"
              autoComplete="current-password"
            />
          )}
        />

        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{mt: 3, mb: 2}}
        >
          Confirm
        </Button>
        <Grid container>
          <Grid item>
            <Link href="login" variant="body2">
              {"Return to login"}
            </Link>
          </Grid>
        </Grid>
      </Box>
      <Copyright/>
    </Container>
  );
}

export default ChangePasswordForm;